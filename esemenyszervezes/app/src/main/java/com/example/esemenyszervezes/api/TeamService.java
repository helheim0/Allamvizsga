package com.example.esemenyszervezes.api;

import com.example.esemenyszervezes.pojo.Result;
import com.example.esemenyszervezes.pojo.Team;

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

public interface TeamService {

    /*
     *   Create new team
     */
    @POST("teams")
    Call<Result> createTeam(
            @Header("Authorization") String token,
            @Query("name") String name,
            @Query("description") String description,
            @Query("admin_id") int id
    );

    /*
    Get a user's specific teams
     */
    @GET("teams/{id}")
    Call<List<Team>> listUserTeams(
            @Header("Authorization:close") String token,
            @Path("id") int id
    );

    @GET("teams/{id}/members")
    Call<List<Team>> getMembers(
            @Header("Authorization:close") String token,
            @Path("id") int id
    );
}
