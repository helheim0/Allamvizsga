package com.example.esemenyszervezes.pojo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class User {

    @SerializedName("id")
    @Expose
    private int mId;

    @SerializedName("name")
    @Expose
    private String mUsername;

    @SerializedName("full_name")
    @Expose
    private String mFullname;

    @SerializedName("phone")
    @Expose
    private int mPhone;

    @SerializedName("email")
    @Expose
    private String mEmail;

    //Constructors
    public User(){

    }

    public User(String mUsername, String mFullname, int mPhone, String mEmail){
        this.mUsername = mUsername;
        this.mFullname = mFullname;
        this.mPhone = mPhone;
        this.mEmail = mEmail;
    }

    //Initializing getters and setters
    public Integer getId() {
        return mId;
    }

    public void setId(Integer mId) {
        this.mId = mId;
    }

    public String getUsername(){
        return mUsername;
    }

    public String getFullName(){
        return mFullname;
    }

    public int getPhone(){
        return mPhone;
    }

    public String getEmail(){
        return mEmail;
    }

    public void setUsername(){
        this.mUsername = mUsername;
    }

    public void setFullName(){
        this.mFullname = mFullname;
    }

    public void setPhone(){
        this.mPhone = mPhone;
    }

    public void setEmail(){
        this.mEmail = mEmail;
    }

    @NonNull
    @Override
    public String toString() {
        return "Data{" +
                "name='" + mUsername + '\'' +
                ", full_name='" + mFullname + '\'' +
                ", phone='" + mPhone + '\'' +
                ", email='" + mEmail + '\'' +
                '}';
    }
}
