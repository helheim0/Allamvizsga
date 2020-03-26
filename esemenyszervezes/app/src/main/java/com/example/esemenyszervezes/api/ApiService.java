package com.example.esemenyszervezes.api;

import com.example.esemenyszervezes.pojo.Event;
import com.example.esemenyszervezes.pojo.Result;
import com.example.esemenyszervezes.pojo.Team;
import com.example.esemenyszervezes.pojo.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    /*
    * Create new user
     */

    @POST("register")
    @FormUrlEncoded
    Call<Result> createUser(
            @Field("name") String username,
            @Field("full_name") String fullName,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password);

    /*
    Log in
     */
    @FormUrlEncoded
    @POST("login")
    Call<Result> loginUser(
            @Field("email") String email,
            @Field("password") String password);

    @GET("users")
    Call<List<User>> getUsers(@Header("Authorization") String token);

    //Get one specific user
    @GET("users/{id}")
    Call<User> getUserWithID(@Path("id") int id);

    //Delete a specific user
    @DELETE("users/{id}")
    Call<User> deleteUserWithID(@Path("id") int id);

    /*
    *   Create new team
     */
    @FormUrlEncoded
    @Multipart
    @POST("teams")
    Call<Result> createTeam(
      @Field("name") String name
      //@Field("image") String image
      //@Part MultipartBody.Part image
    //  @Field("admin_id") String admin
    );

    /*
    List events
     */
    @GET("events")
    Call<List<Event>> listEvents();

    /*
    List teams
     */
    @GET("teams")
    Call<List<Team>> listTeams();
}
