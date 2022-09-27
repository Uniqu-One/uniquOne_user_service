package com.sparos.uniquone.msauserservice.users.typeEnum;

public enum UserGrade {
    RED("빨"),
    ORANGE("주"),
    YELLOW("노"),
    GREEN("초"),
    BLUE("파"),
    NAVY("남"),
    PURPLE("보");

    String grade;
    UserGrade(String grade){
        this.grade = grade;
    }

    public String value(){
        return grade;
    }
}
