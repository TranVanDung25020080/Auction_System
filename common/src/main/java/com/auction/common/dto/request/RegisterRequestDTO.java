package com.auction.common.dto.request;

import com.auction.common.enums.UserRole;

public class RegisterRequestDTO {
    private String username;
    private String ownerName;
    private String password;
    private UserRole role;

    public RegisterRequestDTO(String username, String ownerName, String password, UserRole role) {
        this.username = username;
        this.ownerName = ownerName;
        this.password = password;
        this.role = role;
    }

    //Getter
    //region
    public String getUsername() {return username;}
    public String getEmail() {return ownerName;}
    public String getPassword() {return password;}
    public UserRole getRole() {return role;}
    //endregion
}
