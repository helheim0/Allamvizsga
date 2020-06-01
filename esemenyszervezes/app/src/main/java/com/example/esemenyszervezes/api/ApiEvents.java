package com.example.esemenyszervezes.api;

import com.example.esemenyszervezes.pojo.Event;
import com.example.esemenyszervezes.pojo.Result;
import com.example.esemenyszervezes.pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEvents {
    /*
    Create events
     */
    @POST("events")
    Call<Result> createEvent(
            @Header("Authorization:close") String token,
            @Query("name") String name,
            @Query("date") String date,
            @Query("location") String location,
            @Query("description") String description,
            @Query("admin") int admin,
            @Query("team") int teamId
    );

    /*
    List events
     */
    @GET("events/{id}")
    Call<List<Event>> listEvents(
            @Header("Authorization:close") String token,
            @Path("id") int id
    );

    @GET("events/{id}")
    Call<Event> showEventInvitation(
            @Header("Authorization") String token,
            @Path("id") int id
    );

    @GET("events/{id}/going")
    Call<List<User>> goingParticipants(
            @Header("Authorization:close") String token,
            @Path("id") int id
    );

    @GET("events/{id}/notGoing")
    Call<List<User>> notGoingParticipants(
            @Header("Authorization:close") String token,
            @Path("id") int id
    );

    /*
    *   Accept and decline event invitation
     */
    @POST("events/acceptEvent/{id}/{user}")
    Call<Result> acceptEvent(
            @Header("Authorization:close") String token,
            @Path("id") int id,
            @Path("user") int user
    );

    @POST("events/declineEvent/{id}/{user}")
    Call<Result> declineEvent(
            @Header("Authorization:close") String token,
            @Path("id") int id,
            @Path("user") int user
    );
}
