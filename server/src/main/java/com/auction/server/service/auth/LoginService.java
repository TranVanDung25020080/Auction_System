package com.auction.server.service.auth;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.model.User.User;
import com.auction.server.dao.UserDAO;
import com.auction.server.exception.DatabaseException;

import javax.naming.AuthenticationException;

public class LoginService {
    public UserResponseDTO login(String userName,String password) throws DatabaseException, AuthenticationException {
        UserResponseDTO userResponseDTO=null;

        User user=new UserDAO().login(userName,password);

        if (user==null){
            throw new AuthenticationException("wrong username or password!");
        }

        return userResponseDTO;
    }
}
