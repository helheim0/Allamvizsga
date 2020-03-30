package com.example.esemenyszervezes.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserList {
    @SerializedName("data")
    private List<User> users= null;
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
