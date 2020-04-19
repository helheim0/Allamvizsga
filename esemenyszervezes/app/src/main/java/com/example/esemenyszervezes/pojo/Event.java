package com.example.esemenyszervezes.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Event implements Parcelable {

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
    public Event(){

    }

    public Event(String mName, String mDate, String mLocation, String mDescription){
        this.mName = mName;
        this.mDate = mDate;
        this.mLocation = mLocation;
        this.mDescription = mDescription;
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

    // Parcelling part
    protected Event(Parcel in){
        mName = in.readString();

       /* String[] data = new String[3];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.mName = data[0];*/
      /*  this.mDescription = data[1];
        this.mLocation = data[2];
        this.mDate = data[3];*/
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
    /*public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };*/
}
