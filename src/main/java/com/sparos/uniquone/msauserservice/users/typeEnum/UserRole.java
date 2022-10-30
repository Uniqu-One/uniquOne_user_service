package com.sparos.uniquone.msauserservice.users.typeEnum;

public enum UserRole {
    ROLE_PHONE_PAINUSER("ROLES_PHONE_PAINUSER"),
    ROLES_GUEST("ROLES_GUEST"),
    ROLES_USER("ROLES_USER"),
    ROLES_ADMIN("ROLES_ADMIN");

    String role;

    UserRole(String role){
        this.role = role;
    }
    public String value(){
        return role;
    }
}
