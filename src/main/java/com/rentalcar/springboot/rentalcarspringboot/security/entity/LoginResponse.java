package com.rentalcar.springboot.rentalcarspringboot.security.entity;

public class LoginResponse {
    private final String jwt;
    private final String username;
    private final Boolean superUser;

    public LoginResponse(String jwt, String username, Boolean superUser) {
        this.jwt = jwt;
        this.username = username;
        this.superUser = superUser;
    }

    public String getJwt() {
        return jwt;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getSuperUser() {
        return superUser;
    }
}
