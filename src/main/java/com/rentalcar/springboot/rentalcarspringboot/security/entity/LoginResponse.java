package com.rentalcar.springboot.rentalcarspringboot.security.entity;

public class LoginResponse {
    private final String jwt;
    private final int id;
    private final String username;
    private final String role;

    public LoginResponse(String jwt, int id, String username, String role) {
        this.jwt = jwt;
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
