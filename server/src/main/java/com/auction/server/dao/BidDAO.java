package com.auction.server.dao;

import com.auction.common.model.Auction.BidTransaction;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.DatabaseException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BidDAO {

    UserDAO userDAO = new UserDAO();
    AuctionDAO auctionDAO = new AuctionDAO();

    public void placeBid(int auctionId, int bidderId, double bidAmount) throws DatabaseException {
        String insertBidQuery = "INSERT INTO bid_transaction (auctionId, bidderId, bidAmount, bidTime) VALUES (?, ?, ?, NOW())";

        double bidderBalance = userDAO.showBalance(bidderId);

        try (Connection conn = MyDatabaseConfig.getConnection();
            PreparedStatement pst = conn.prepareStatement(insertBidQuery)) {
            if (bidderBalance >= bidAmount) {
                pst.setInt(1, auctionId);
                pst.setInt(2, bidderId);
                pst.setDouble(3, bidAmount);

                int result = pst.executeUpdate();
                if (result > 0) {
                    auctionDAO.updateCurrentPrice(auctionId, bidAmount, bidderId);
                }
            }
            else {
                throw new DatabaseException("Khong du so du de dat gia");
            }
        }
        catch (SQLException e) {
            System.err.println("Loi SQL o ham placeBid: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the dat gia", e);
        }
    }

    public List<BidTransaction> getBidsByAuctionId(int auctionId) throws DatabaseException {
        List<BidTransaction> list = new ArrayList<>();
        String query = "SELECT * FROM bid_transaction WHERE auctionId = ?;";

        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, auctionId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                BidTransaction bid = new BidTransaction(
                        rs.getInt("transactionId"),
                        rs.getInt("auctionId"),
                        rs.getInt("bidderId"),
                        rs.getDouble("bidAmount"),
                        rs.getTimestamp("bidTime").toLocalDateTime()
                );
                list.add(bid);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Không thể lấy lịch sử đấu giá.", e);
        }
        return list;
    }
    public List<BidTransaction> getBidInfoByBidderId(int bidderId,int auctionId) throws SQLException {
        List<BidTransaction> bidTransactionList=new ArrayList<>();

        String query="SELECT * FROM bid_transaction WHERE bidderId=? and auctionId=?";

        try (Connection connection=MyDatabaseConfig.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(query);

            preparedStatement.setInt(1,bidderId);
            preparedStatement.setInt(2,auctionId);

            try (ResultSet resultSet= preparedStatement.executeQuery()){
                while (resultSet.next()){
                    //int transactionId, int auctionId, int bidderId, double bidAmount, LocalDateTime bidTime
                    BidTransaction bidTransaction=new BidTransaction(resultSet.getInt("transactionId"),
                            resultSet.getInt("auctionId"),
                            resultSet.getInt("bidderId"),
                            resultSet.getDouble("bidAmount"),
                            resultSet.getTimestamp("bidTime").toLocalDateTime());

                    bidTransactionList.add(bidTransaction);

                }

            }

            return bidTransactionList;

        }
    }


    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /*//test
    static void main(String[] args) throws SQLException {
        for (BidTransaction bidTransaction:new BidDAO().getBidInfoByBidderId(1,1)){
            System.out.println(bidTransaction);
        }
    }*/
}