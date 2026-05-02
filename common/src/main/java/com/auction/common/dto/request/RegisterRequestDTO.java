package com.auction.common.dto.request;

import com.auction.common.enums.UserRole;

public class RegisterRequestDTO {
    private String userName;
    private String ownerName;
    private String password;
    private String role;

    public RegisterRequestDTO(String userName, String ownerName, String password, String role) {
        this.userName = userName;
        this.ownerName = ownerName;
        this.password = password;
        this.role = role;
    }

    //Getter
    //region
    public String getUsername() {return userName;}
    public String getEmail() {return ownerName;}
    public String getPassword() {return password;}
    public String getRole() {return role;}
    //endregion
}
