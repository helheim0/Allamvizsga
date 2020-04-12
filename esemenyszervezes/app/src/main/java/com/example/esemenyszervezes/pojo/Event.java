package com.example.esemenyszervezes.pojo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Event {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("date")
    @Expose
    private String mDate;

    @SerializedName("location")
    @Expose
    private String mLocation;

    @SerializedName("description")
    @Expose
    private String mDescription;

    @SerializedName("admin")
    @Expose
    private int mAdminId;

    //Constructors
   /* public Event(){

    }

    public Event(String mName, String mDate, String mLocation, String mDescription){
        this.mName = mName;
        this.mDate = mDate;
        this.mLocation = mLocation;
        this.mDescription = mDescription;
    }*/

    //Initializing getters and setters
    public String getName(){
        return mName;
    }

    public String getDate(){
        return mDate;
    }

    public String getLocation(){
        return mLocation;
    }

    public String getDescription(){
        return mDescription;
    }

    public int getEventId(){
        return id;
    }

    public int getAdminId(){
        return mAdminId;
    }

    public void setName(){
        this.mName = mName;
    }

    public void setDate(){
        this.mDate = mDate;
    }

    public void setLocation(){
        this.mLocation = mLocation;
    }

    public void setDescription(){
        this.mDescription = mDescription;
    }

    /*@NonNull
    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", name='" + mName + '\'' +
                ", date='" + mDate + '\'' +
                ", location='" + mLocation + '\'' +
                ", description='" + mDescription + '\'' +
                ", admin='" + mAdminId + '\'' +
                '}';
    }*/
}
