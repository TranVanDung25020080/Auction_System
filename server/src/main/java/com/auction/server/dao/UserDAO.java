package com.auction.server.dao;

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

    private static final Map<Class<? extends User>, UserRegister> registerParameter = new HashMap<>();

    static {
        userCreators.put("BIDDER", new BidderLogin());
        userCreators.put("SELLER", new SellerLogin());
        userCreators.put("ADMIN", new AdminLogin());

        registerParameter.put(Admin.class, new AdminRegister());
        registerParameter.put(Seller.class, new SellerRegister());
        registerParameter.put(Bidder.class, new BidderRegister());
    }

    public User login(String userName, String password) throws DatabaseException {
        String query = "select * from user where username= ? and password= ?";

        /*try (Connection conn = DatabaseConnection.getConnection();*/
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

    public boolean registerUser(User user, String password) throws DatabaseException {
        String query = "INSERT INTO user (userId, ownerName, userName, password, role, role_level, shop_name, rating, balance) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        /*try (Connection conn = DatabaseConnection.getConnection();*/
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, user.getId());
            pst.setString(2, user.getOwnerName());
            pst.setString(3, user.getUserName());
            pst.setString(4,password);

            UserRegister ur = registerParameter.get(user.getClass());

            if (ur == null) {
                throw new IllegalArgumentException("He thong chua ho tro dang ki cho loai User nay!!");
            }
            ur.setParameter(pst, user);

            return pst.executeUpdate() > 0;
        }
        catch (SQLException e) {
            System.err.println("Loi SQL o ham Register: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the luu thong tin nguoi dung!!",e);
        }
    }

    public boolean updateBalance(int userId, double amount) throws Exception {
        String query = "UPDATE user SET balance = balance + ? WHERE userId = ? AND (role = 'BIDDER' or role = 'SELLER')";

        /*try (Connection conn = DatabaseConnection.getConnection();*/
        try (Connection conn = MyDatabaseConfig.getConnection();
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
