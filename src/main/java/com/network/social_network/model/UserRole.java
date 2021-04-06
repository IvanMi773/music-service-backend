package com.network.social_network.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER("USER"),
    ADMIN("ADMIN");

    private String role;

    UserRole (String role) {
        this.role = role;
    }

    public String getRole () {
        return role;
    }

    @Override
    public String getAuthority () {
        return role;
    }
}
