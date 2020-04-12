package com.example.esemenyszervezes.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team {

    @SerializedName("id")
    @Expose
    private int mId;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("image")
    @Expose
    private String mImage;

    @SerializedName("admin_id")
    @Expose
    private int mAdminId;

    //Constructors
    public Team(){ }

    public Team(String mName, String mImage){
        this.mName = mName;
        this.mImage = mImage;
    }

    //Initializing getters and setters
    public String getName(){
        return mName;
    }

    public String getImage(){
        return mImage;
    }

    public void setName(){
        this.mName = mName;
    }

    public void setImage(){
        this.mImage = mImage;
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + mName + '\'' +
                ", image='" + mImage + '\'' +
                '}';
    }
}
