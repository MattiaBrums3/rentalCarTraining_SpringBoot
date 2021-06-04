package com.rentalcar.springboot.rentalcarspringboot.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private int id;
    private String username;
    private String password;
    private Boolean superUser;

    public UserDetails() {}

    public UserDetails(String username, String password, Boolean superUser) {
        this.username = username;
        this.password = password;
        this.superUser = superUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (superUser) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        }
    }

    public int getId() {
        return id;
    }
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public boolean getSuperUser() {
        return superUser;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
