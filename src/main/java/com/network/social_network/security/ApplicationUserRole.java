package com.network.social_network.security;

public enum ApplicationUserRole {
    STUDENT("STUDENT"),
    ADMIN("ADMIN");

    private String role;

    ApplicationUserRole (String role) {
        this.role = role;
    }

    public String getRole () {
        return role;
    }
}
