package com.network.social_network.dto.user;

public class UserAuthenticationDto {

    private String username;
    private String password;

    public UserAuthenticationDto () {
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }
}
