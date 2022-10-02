package com.sparos.uniquone.msauserservice.users.typeEnum;

public enum UserRole {
    ROLE_PHONE_PAINUSER("ROLE_PHONE_PAINUSER"),
    ROLES_GUEST("ROLE_GUEST"),
    ROLES_USER("ROLE_USER"),
    ROLES_ADMIN("ROLE_ADMIN");

    String role;

    UserRole(String role){
        this.role = role;
    }
    public String value(){
        return role;
    }
}
