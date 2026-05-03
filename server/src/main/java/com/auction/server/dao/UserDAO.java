package com.auction.server.dao;

import com.auction.common.enums.UserRole;
import com.auction.common.model.User.Admin;
import com.auction.common.model.User.Bidder;
import com.auction.common.model.User.Seller;
import com.auction.common.model.User.User;
import com.auction.server.db.DatabaseConnection;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.DatabaseException;
import com.auction.server.dp.factory.UFac.login.*;
import com.auction.server.dp.factory.UFac.register.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
/*        try (Connection conn = DatabaseConnection.getConnection();*/
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

    public boolean updateBalance(int userId, double amount) throws Exception {
        String query = "UPDATE user SET balance = balance + ? WHERE userId = ? AND (role = 'BIDDER' or role = 'SELLER')";

        try (Connection conn = DatabaseConnection.getConnection();
//        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setDouble(1, amount);
            pst.setInt(2, userId);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Loi SQL o ham UpdateBalance: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the cap nhat so du!!",e);
        }
    }
}
