package com.example.esemenyszervezes.api;

import com.example.esemenyszervezes.pojo.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface ApiEvents {
    /*
    Create events
     */
    @FormUrlEncoded
    @Multipart
    @POST("events")
    Call<List<Event>> createEvent(
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("date") String date,
            @Field("location") String location,
            @Field("description") String description
            //  @Field("admin_id") String admin
    );
    /*
    List events
     */
    @GET("events")
    Call<List<Event>> listEvents();
//    Call<List<Event>> listEvents(@Header("Authorization") String token);

}
