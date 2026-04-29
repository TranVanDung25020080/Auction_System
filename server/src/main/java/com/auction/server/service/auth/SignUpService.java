package com.auction.server.service.auth;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.model.User.User;
import com.auction.server.dao.UserDAO;
import com.auction.server.exception.DatabaseException;

public class SignUpService {
    public UserResponseDTO singup(User user,String password) throws DatabaseException {
        UserResponseDTO userResponseDTO=null;

        if (new UserDAO().registerUser(user,password)){
            userResponseDTO=new UserResponseDTO(user);
        }

        return userResponseDTO;
    }

}
