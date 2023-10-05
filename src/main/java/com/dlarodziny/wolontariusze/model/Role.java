package com.dlarodziny.wolontariusze.model;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    private final String roleDesc;

    Role(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRole() {
        return this.roleDesc;
    }
    
}