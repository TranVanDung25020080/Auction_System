package com.auction.server.auth;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.UserRole;
import com.auction.server.dao.UserDAO;
import com.auction.server.exception.DatabaseException;

public class SignUpService {
    private final UserDAO userDAO = new UserDAO();

    public UserResponseDTO signUp(String ownerName, String userName, String password, UserRole role) throws DatabaseException {
        UserResponseDTO userResponseDTO = null;

        if (userDAO.registerUser(ownerName, userName, password, role)) {
            userResponseDTO = new UserResponseDTO(ownerName, userName, role);
        }
        return userResponseDTO;
    }
}
