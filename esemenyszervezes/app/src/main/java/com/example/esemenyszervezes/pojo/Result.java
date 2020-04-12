package com.example.esemenyszervezes.pojo;

import com.example.esemenyszervezes.pojo.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("error")
    @Expose
    private Boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("access_token")
    @Expose
    private String access_token;

    @SerializedName("token_type")
    @Expose
    private String token_type;

    @SerializedName("id")
    @Expose
    private int id;

    public Result(Boolean error, String message, String access_token, String token_type, int id) {
        this.error = error;
        this.message = message;
        this.access_token = access_token;
        this.token_type = token_type;
        this.id = id;
    }

    //Getters + setters
    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public int getUserId() {
        return id;
    }

    public void setAccess_token(){
        this.access_token = access_token;
    }

    public void setToken_type(){
        this.token_type = token_type;
    }

    public void setId(){
        this.id = id;
    }
}
