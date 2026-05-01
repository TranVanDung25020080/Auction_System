package com.auction.common.dto.request;

public class LoginRequestDTO {
    private String userName;
    private String password;

    public LoginRequestDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    //Getter
    //region
    public String getUserName() {
        return userName;
    }
    public String getPassword() { return password; }
    //endregion
}
