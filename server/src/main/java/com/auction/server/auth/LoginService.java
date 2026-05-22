package com.auction.server.auth;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.model.User.User;
import com.auction.server.dao.UserDAO;
import com.auction.server.exception.AuthException;
import com.auction.server.exception.DatabaseException;

public class LoginService {
    private final UserDAO userDAO = new UserDAO();

    public UserResponseDTO login(String userName, String password) throws DatabaseException, AuthException {
        UserResponseDTO userResponseDTO = null;

        User user = userDAO.login(userName, password);

        if (user == null) {
            throw new AuthException("wrong username or password!");
        } else {
            userResponseDTO = new UserResponseDTO(
                    user.getUserId(),
                    user.getOwnerName(),
                    user.getUserName(),
                    user.getBalance(),
                    user.getUserRole()
            );
        }
        return userResponseDTO;
    }
}