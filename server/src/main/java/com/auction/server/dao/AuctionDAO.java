package com.auction.server.dao;

import com.auction.common.enums.AuctionStatus;
import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Auction.Auction;
import com.auction.common.model.Item.Item;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.DatabaseException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuctionDAO {

    private ItemDAO itemDAO = new ItemDAO();

    public boolean createAuction(int auctionId, int itemId, LocalDateTime startTime, LocalDateTime endTime) throws DatabaseException {
        String query = "INSERT INTO auction (auctionId, itemId, currentHighestPrice, winningBidderId, startTime, endTime, status, item_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String query1 = "UPDATE item SET item_status = ? WHERE item_id = ?";

        Item item = itemDAO.getItemById(itemId);

/*        try (Connection conn = DatabaseConnection.getConnection();*/
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             PreparedStatement pst1 = conn.prepareStatement(query1)) {

            pst.setInt(1, auctionId);
            pst.setInt(2, itemId);
            pst.setDouble(3, item.getInitialPrice());
            pst.setNull(4, Types.INTEGER);
            pst.setTimestamp(5, Timestamp.valueOf(startTime));
            pst.setTimestamp(6, Timestamp.valueOf(endTime));
            pst.setString(7, "PENDING");
            pst.setString(8, item.getName());

            pst1.setString(1, ItemStatus.AUCTION.name());
            pst1.setInt(2, itemId);

            int change = pst.executeUpdate() + pst1.executeUpdate();
            return (change > 1);

        }
        catch (SQLException e) {
            System.out.println("Loi SQL o ham updateCurrentPrice: " + e.getMessage());
            throw new DatabaseException("Loi he thong: Khong the tao cuoc dau gia", e);
        }
    }

    public void updateCurrentPrice(int auctionId, double newPrice, int bidderId) throws DatabaseException {
        String query = "UPDATE auction SET currentHighestPrice = ? , winningBidderId = ? WHERE auctionId = ?";

/*        try (Connection conn = DatabaseConnection.getConnection();*/
          try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setDouble(1,newPrice);
            pst.setInt(2, bidderId);
            pst.setInt(3, auctionId);
            
            pst.executeUpdate();

        }
        catch (SQLException e) {
            System.err.println("Loi SQL o ham updateCurrentPrice: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the cap nhat gia vat pham",e);
        }
    }

    public Auction getAuctionInfoById(int auctionId) throws DatabaseException {
        String query = "SELECT * FROM auction WHERE auctionId = ?";
/*
        try (Connection conn = DatabaseConnection.getConnection();*/
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, auctionId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Auction(rs.getInt("auctionId"),
                            rs.getInt("itemId"),
                            rs.getDouble("currentHighestPrice"),
                            rs.getInt("winningBidderId"),
                            rs.getTimestamp("startTime").toLocalDateTime(),
                            rs.getTimestamp("endTime").toLocalDateTime(),
                            AuctionStatus.valueOf(rs.getString("status")),
                            rs.getString("item_name"));
                }
            }
        }
        catch (SQLException e) {
            System.err.println("Loi SQL o ham getAuctionInfoById: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the xem thong tin cuoc dau gia",e);
        }
        return null;
    }

    public List<Auction> getAllAuction() throws SQLException {
        List<Auction> auctionList=new ArrayList<>();

        String sql="SELECT * FROM auction;";

        try (Connection connection=MyDatabaseConfig.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(sql);

            try (ResultSet resultSet=preparedStatement.executeQuery()){
                while (resultSet.next()){
                    auctionList.add(new Auction(resultSet.getInt("auctionId"),
                            resultSet.getInt("itemId"),
                            resultSet.getDouble("currentHighestPrice"),
                            resultSet.getInt("winningBidderId"),
                            resultSet.getTimestamp("startTime").toLocalDateTime(),
                            resultSet.getTimestamp("endTime").toLocalDateTime(),
                            AuctionStatus.valueOf(resultSet.getString("status")),
                            resultSet.getString("item_name")));
                }

            }
        }
        return auctionList;

    }

    public void updateAuctionStatus(AuctionStatus status, int auctionId) throws DatabaseException {
        String query = "UPDATE auction SET status = ? WHERE auctionId = ?";

        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1,status.name());
            pst.setInt(2,auctionId);

            pst.executeUpdate();

        }
        catch (SQLException e) {
            System.err.println("Loi SQL o ham updateAuctionStatus: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the cap nhat trang thai dau gia ",e);
        }

    }

    public void endAuction(int auctionId) throws DatabaseException {
        String query = "UPDATE auction SET status = ? WHERE auctionId = ?";
        String query1 = "UPDATE user SET balance = balance - ? WHERE userId = ?";
        String query2 = "UPDATE item SET item_status = ? WHERE id = ?";

        Auction auctionInfo = getAuctionInfoById(auctionId);

        try (Connection conn = MyDatabaseConfig.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(query);
                 PreparedStatement pst1 = conn.prepareStatement(query1);
                 PreparedStatement pst2 = conn.prepareStatement(query2)) {

                pst.setString(1, AuctionStatus.FINISHED.name());
                pst.setInt(2, auctionId);

                pst1.setDouble(1, auctionInfo.getCurrentHighestPrice());
                pst1.setInt(2, auctionInfo.getWinningBidderId());

                pst2.setString(1, ItemStatus.SOLD.name());
                pst2.setInt(2, auctionInfo.getItemId());

                pst.executeUpdate();
                pst1.executeUpdate();
                pst2.executeUpdate();

                conn.commit();
                System.out.println("Cuoc dau gia co id " + auctionId + " da ket thuc thanh cong!");
            }
            catch (SQLException e) {
                conn.rollback();
                throw new DatabaseException("Loi he thong: khong the dong cuoc dau gia, tu dong rollback",e);
            }
        } catch (SQLException e) {
            System.err.println("Loi SQL o ham updateAuctionStatus: " + e.getMessage());
        }
    }

//    static void main(String[] args) throws DatabaseException, SQLException {
//        AuctionDAO auctionDAO = new AuctionDAO();
//        auctionDAO.endAuction(1);
//        System.out.println("SUCCESS");
//    }

}
