package com.example.esemenyszervezes.api;

import com.example.esemenyszervezes.pojo.Event;
import com.example.esemenyszervezes.pojo.Result;
import com.example.esemenyszervezes.pojo.Team;
import com.example.esemenyszervezes.pojo.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    /**
     * Logout
     */
    @POST("logout")
    Call<Void> logout();

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
    @POST("teams")
    Call<Result> createTeam(
      @Header("Authorization") String token,
      @Field("name") String name,
      @Field("description") String description,
      @Field("admin_id") int admin
    );

    @Multipart
    @POST("teams")
    Call<Result> uploadImage(
            @Header("Authorization") String token,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part image
    );
    /*
    List teams generally
     */
    @GET("teams")
    Call<List<Team>> listTeams();
    //Call<List<Team>> listTeams(@Header("Authorization") String token);

    /*
    Get a user's specific teams
     */
    @GET("teams/{id}")
    Call<List<Team>> listUserTeams(
            @Header("Authorization") String token,
            @Path("admin_id") int id
    );

    /*
    Get a user's specific teams
     */
    @GET("members")
    Call<List<User>> listTeamMembers(
           /* @Header("Authorization") String token,
            @Path("admin_id") int id*/
    );
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
