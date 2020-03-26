package com.example.esemenyszervezes.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Team implements Parcelable {

    @SerializedName("id")
    @Expose
    private int mId;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("image")
    @Expose
    private int mImage;

    //Constructors
    public Team(){

    }

    public Team(String mName, int mImage){
        this.mName = mName;
        this.mImage = mImage;
    }

    //Initializing getters and setters
    public String getName(){
        return mName;
    }

    public int getImage(){
        return mImage;
    }

    public void setName(){
        this.mName = mName;
    }

    public void setImage(){
        this.mImage = mImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
