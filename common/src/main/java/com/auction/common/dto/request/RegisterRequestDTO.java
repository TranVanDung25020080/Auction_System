package com.auction.common.dto.request;

import com.auction.common.enums.UserRole;

public class RegisterRequestDTO {
    private String userName;
    private String ownerName;
    private String password;
    private UserRole role;

    public RegisterRequestDTO(String userName, String ownerName, String password, UserRole role) {
        this.userName = userName;
        this.ownerName = ownerName;
        this.password = password;
        this.role = role;
    }

    public RegisterRequestDTO(String username, String displayName, String password, String role) {
        this.userName = username;
        this.ownerName = displayName;
        this.password = password;
        this.role = UserRole.valueOf(role);
    }

    //Getter
    //region
    public String getUsername() {return userName;}
    public String getEmail() {return ownerName;}
    public String getPassword() {return password;}
    public UserRole getRole() {return role;}
    //endregion
}
