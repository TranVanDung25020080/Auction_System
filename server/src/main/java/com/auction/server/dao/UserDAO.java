package com.auction.server.dao;

import com.auction.common.enums.UserRole;
import com.auction.common.model.User.User;
import com.auction.server.db.DatabaseConnection;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.dp.factory.UFac.login.AdminLogin;
import com.auction.server.dp.factory.UFac.login.BidderLogin;
import com.auction.server.dp.factory.UFac.login.SellerLogin;
import com.auction.server.dp.factory.UFac.login.UserLogin;
import com.auction.server.dp.factory.UFac.register.AdminRegister;
import com.auction.server.dp.factory.UFac.register.BidderRegister;
import com.auction.server.dp.factory.UFac.register.SellerRegister;
import com.auction.server.dp.factory.UFac.register.UserRegister;
import com.auction.server.exception.DatabaseException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private static final Map<String, UserLogin> userCreators = new HashMap<>();

    private static final Map<UserRole, UserRegister> registerParameter = new HashMap<>();

    static {
        userCreators.put("BIDDER", new BidderLogin());
        userCreators.put("SELLER", new SellerLogin());
        userCreators.put("ADMIN", new AdminLogin());

        registerParameter.put(UserRole.ADMIN, new AdminRegister());
        registerParameter.put(UserRole.SELLER, new SellerRegister());
        registerParameter.put(UserRole.BIDDER, new BidderRegister());
    }

    public User login(String userName, String password) throws DatabaseException {
        String query = "select * from user where username= ? and password= ?";

/*        try (Connection conn = DatabaseConnection.getConnection();*/
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, userName);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");

                UserLogin uc = userCreators.get(role.toUpperCase());

                if (uc == null) {
                    throw new IllegalArgumentException("Vai tro cua User chua xac dinh!!");
                }
                return uc.logUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Loi SQL o ham Login: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the truy van du lieu dang nhap!",e);
        }
        return  null;
    }

    public boolean registerUser(String ownerName, String userName, String password, UserRole role) throws DatabaseException {

        String query = "INSERT INTO user (ownerName, userName, password, role, role_level, rating, balance) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try (Connection conn = DatabaseConnection.getConnection();
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, ownerName);
            pst.setString(2, userName);
            pst.setString(3,password);

            UserRegister ur = registerParameter.get(role);

            if (ur == null) {
                throw new IllegalArgumentException("He thong chua ho tro dang ki cho loai User nay!!");
            }
            ur.setParameter(pst);

            return pst.executeUpdate() > 0;
        }
        catch (SQLException e) {
            System.err.println("Loi SQL o ham Register: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the luu thong tin nguoi dung!!",e);
        }
    }

    public void updateBalance(int userId, double amount) throws DatabaseException {
        String query = "UPDATE user SET balance = ? WHERE userId = ? AND (role = 'BIDDER' or role = 'SELLER')";

      /*  try (Connection conn = DatabaseConnection.getConnection())*/
        try (Connection conn = MyDatabaseConfig.getConnection()){
            conn.setAutoCommit(false);
            try (PreparedStatement pst = conn.prepareStatement(query)) {

                pst.setDouble(1, amount);
                pst.setInt(2, userId);

                int change = pst.executeUpdate();
                if (change > 0) {
                    conn.commit();
                    System.out.println("Cap nhat so du thanh cong!");
                }
                System.out.println("Cap nhat so du that bai!");
            }
            catch (SQLException e) {
                conn.rollback();
                throw new DatabaseException("Loi he thong: khong the cap nhat so du, tu dong rollback",e);
            }
        }
        catch (SQLException e) {
            System.err.println("Loi SQL o ham UpdateBalance: " + e.getMessage());
        }
    }

    public double showBalance(int userId) throws DatabaseException {
        String query = "SELECT balance FROM user WHERE userId = ?";

/*        try (Connection conn = DatabaseConnection.getConnection();*/
        try (Connection conn = MyDatabaseConfig.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, userId);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("So du cua nguoi dung " + userId + " la: " + rs.getBigDecimal("balance"));
                return rs.getDouble("balance");
            }
            else {
                throw new DatabaseException("Khong tim thay id nguoi dung nay");
            }
        }
        catch (SQLException e) {
            System.err.println("Loi SQL o ham showBalance: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the lay so du cua user nay", e);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList=new ArrayList<>();

        String query="SELECT * FROM user;";

        try (Connection connection=MyDatabaseConfig.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(query);

            try (ResultSet resultSet=preparedStatement.executeQuery()){
                while (resultSet.next()){
                    //int userId, String ownerName, String userName, double balance
                    User user=new User(resultSet.getInt("userId"),
                            resultSet.getString("ownerName"),
                            resultSet.getString("userName"),
                            resultSet.getDouble("balance"));
                    user.setUserRole(UserRole.valueOf(resultSet.getString("role")));

                    userList.add(user);

                }
            }
            return userList;
        }
    }





}
