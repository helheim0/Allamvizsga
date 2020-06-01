package com.example.esemenyszervezes.api;

import com.example.esemenyszervezes.pojo.EmailResponse;
import com.example.esemenyszervezes.pojo.Result;
import com.example.esemenyszervezes.pojo.Team;
import com.example.esemenyszervezes.pojo.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    /*
    * Create new user
     */
    @FormUrlEncoded
    @POST("register")
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

    /*
    * Search for a user
    *
     */
    @GET("users/search/{query}")
    Call<List<User>> getUsers(@Header("Authorization") String token, @Path("query") String query);

    //Get one specific user
    @GET("users/{id}")
    Call<User> getUserWithID(@Path("id") int id);

    //Delete a specific user
    @DELETE("users/{id}")
    Call<User> deleteUserWithID(@Path("id") int id);


    /*
    * Invite user + send email
     */
    @FormUrlEncoded
    @POST("teams/send")
    Call<Result> inviteUser(@Header("Authorization:close") String token,
                                   @Field("email") String email,
                                   @Field("name") String name,
                                   @Field("code") String code
                          );

    /*
    * Search for team with code/name
     */

    @GET("teams/search/{query}")
    Call<List<Team>> searchTeams(@Header("Authorization") String token, @Path("query") String query);
}
