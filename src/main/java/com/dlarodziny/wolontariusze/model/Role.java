package com.dlarodziny.wolontariusze.model;

public enum Role {
    ADMIN("Administrator", "ROLE_ADMIN"),
    USER("Wolontariusz", "USER_ROLE");

    private final String roleDesc;
    private final String securityRoleName;

    Role(String roleDesc, String securityRoleName) {
        this.roleDesc = roleDesc;
        this.securityRoleName = securityRoleName;
    }

    public String getRoleDesc() {
        return this.roleDesc;
    }
    
    public String getSecurityRole() {
        return this.securityRoleName;
    }
    
}