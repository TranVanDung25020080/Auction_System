package com.auction.server.service.auth;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.model.User.User;
import com.auction.server.dao.UserDAO;
import com.auction.server.exception.AuthException;
import com.auction.server.exception.DatabaseException;

import javax.naming.AuthenticationException;

public class LoginService {
    public UserResponseDTO login(String userName,String password) throws DatabaseException, AuthException {
        UserResponseDTO userResponseDTO=null;

        User user=new UserDAO().login(userName,password);

        if (user==null){
            throw new AuthException("wrong username or password!");
        }
        else{

            userResponseDTO=new UserResponseDTO(user);
        }

        return userResponseDTO;
    }
}
