package com.example.esemenyszervezes.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamList {
    private Team[] data;

    public Team[] getData ()
    {
        return data;
    }

    public void setData (Team[] data)
    {
        this.data = data;
    }
}
