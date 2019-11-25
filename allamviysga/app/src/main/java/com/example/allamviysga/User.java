package com.example.allamviysga;

public class User {
    private String fullName, userName, email;
    private int phone;

    public User(){

    }

    public User(String fullName, String userName,String email, int phone){
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }
}
