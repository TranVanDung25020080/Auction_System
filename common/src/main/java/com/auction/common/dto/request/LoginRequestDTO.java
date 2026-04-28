package com.auction.common.dto.request;

public class LoginRequestDTO {
    private String email;
    private String password;

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //Getter
    //region
    public String getEmail() {
        return email;
    }
    public String getPassword() { return password; }
    //endregion
}
