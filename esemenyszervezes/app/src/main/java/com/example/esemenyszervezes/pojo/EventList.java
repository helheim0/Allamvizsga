package com.example.esemenyszervezes.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventList {
    @SerializedName("data")
    private List events;
    public List getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
