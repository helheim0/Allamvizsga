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

    @SerializedName("description")
    @Expose
    private String mDescription;

    @SerializedName("image")
    @Expose
    private String mImage;

    @SerializedName("admin_id")
    @Expose
    private int mAdminId;

    //Constructors
    public Team(){ }

   /* public Team(String mName, String mDescription, String mImage){
        this.mName = mName;
        this.mDescription = mDescription;
        this.mImage = mImage;
    }*/

    public Team(String mName, String mDescription){
        this.mName = mName;
        this.mDescription = mDescription;
    }

    protected Team(Parcel in) {
        mName = in.readString();
        mDescription = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mDescription);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    //Initializing getters and setters
    public String getName(){
        return mName;
    }

    public String getDescription(){
        return mDescription;
    }

    public String getImage(){
        return mImage;
    }

    public void setName(){
        this.mName = mName;
    }

    public void setDescription(){
        this.mDescription = mDescription;
    }

    public void setImage(){
        this.mImage = mImage;
    }

}
