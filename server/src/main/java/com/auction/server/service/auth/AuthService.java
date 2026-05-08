package com.auction.server.service.auth;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.model.User.User;
import com.auction.server.dao.UserDAO;
import com.auction.server.exception.DatabaseException;

public class AuthService {
    private final UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public UserResponseDTO login(String username, String password) {
        UserResponseDTO response = new UserResponseDTO();
        try {
            User user = userDAO.getUserByUsername(username);

            if (user == null) {
                response.setMessage("User not found");
                return response;
            }

            String dbPassword = (user.getPassword() != null) ? user.getPassword().trim() : "";
            String inputPassword = (password != null) ? password.trim() : "";

            if (!dbPassword.equals(inputPassword)) {
                response.setMessage("Invalid password");
                return response;
            }

            response.setMessage("Login successfully");

        } catch (DatabaseException e) {
            response.setMessage("Database error: " + e.getMessage());
        }
        return response;
    }
}