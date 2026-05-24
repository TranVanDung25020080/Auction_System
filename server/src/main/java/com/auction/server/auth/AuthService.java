package com.auction.server.auth;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.UserRole;
import com.auction.common.model.User.User;
import com.auction.server.dao.UserDAO;
import com.auction.server.exception.DatabaseException;

public class AuthService {
    private final UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserResponseDTO login(String username, String password) {
        UserResponseDTO response = new UserResponseDTO();

        if (username != null) username = username.trim();
        if (password != null) password = password.trim();

        try {
            User user = userDAO.getUserByUsername(username);

            if (user == null) {
                response.setMessage("User not found");
                return response;
            }

            String dbPassword = (user.getPassword() != null) ? user.getPassword().trim() : "";

            if (!dbPassword.equalsIgnoreCase(password)) {
                response.setMessage("Invalid password");
                return response;
            }

            response.setUserName(user.getUserName());
            response.setUserRole(user.getUserRole());

            response.setMessage("Login successfully");
            return response;

        } catch (DatabaseException e) {
            response.setMessage("Database error: " + e.getMessage());
        }
        return response;
    }

    public UserResponseDTO registerUser(String ownerName, String username, String password, UserRole role) {
        UserResponseDTO response = new UserResponseDTO();

        // Kiểm tra dữ liệu đầu vào cơ bản để tránh NullPointerException
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            response.setMessage("Username and password cannot be empty");
            return response;
        }

        try {
            User existingUser = userDAO.getUserByUsername(username.trim());
            if (existingUser != null) {
                response.setMessage("Username already exists");
                return response;
            }

            //Nếu chưa trùng, gọi DAO để lưu xuống Database
            boolean isSaved = userDAO.registerUser(
                    ownerName != null ? ownerName.trim() : "",
                    username.trim(),
                    password.trim(),
                    role
            );

            if (isSaved) {
                response.setMessage("Register successfully");
            } else {
                response.setMessage("Register failed");
            }

        } catch (DatabaseException e) {
            // Bắt lỗi hệ thống cơ sở dữ liệu
            response.setMessage("Database error: " + e.getMessage());
        }

        return response;
    }
}