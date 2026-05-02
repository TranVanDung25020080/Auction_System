package com.auction.server.service.auth;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.UserRole;
import com.auction.common.model.User.Bidder;
import com.auction.common.model.User.Seller;
import com.auction.common.model.User.User;
import com.auction.server.dao.UserDAO;
import com.auction.server.exception.DatabaseException;

public class SignUpService {
    public UserResponseDTO signUp(String ownerName, String userName, String password, String role) throws DatabaseException {
        UserResponseDTO userResponseDTO = null;
        User user = null;

        if (role.equalsIgnoreCase("BIDDER")) {
            user = new Bidder(ownerName, userName, password);
        } else if (role.equalsIgnoreCase("SELLER")){
            user = new Seller(ownerName, userName, password);
        }


        if (new UserDAO().registerUser(user, password)) {

            new UserDAO().registerUser(user,password);



        }

        return userResponseDTO;


    }

}
