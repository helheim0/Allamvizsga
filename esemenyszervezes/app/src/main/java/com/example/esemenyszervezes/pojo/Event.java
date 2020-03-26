package com.example.esemenyszervezes.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Event implements Parcelable {

    @SerializedName("id")
    @Expose
    private int mEventId;

    @SerializedName("admin")
    @Expose
    private int mAdminId;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("date")
    @Expose
    private String mDate;

    @SerializedName("location")
    @Expose
    private String mLocation;

    @SerializedName("image")
    @Expose
    private int mImage;

    @SerializedName("description")
    @Expose
    private String mDescription;

    //Constructors
    public Event(){

    }

    public Event(String mName, String mDate, String mLocation, String mDescription, int mImage){
        this.mName = mName;
        this.mDate = mDate;
        this.mLocation = mLocation;
        this.mDescription = mDescription;
        this.mImage = mImage;
    }

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

    public int getImage(){
        return mImage;
    }

    public int getEventId(){
        return mEventId;
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

    public void setmDescription(){
        this.mDescription = mDescription;
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
        dest.writeString(this.mDate);
        dest.writeString(this.mLocation);
        dest.writeString(this.mDescription);
        dest.writeString(this.mName);
        dest.writeValue(this.mImage);
    }
}
