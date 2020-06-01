package com.example.esemenyszervezes.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    public EmailResponse(Boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    //Getters + setters
    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
