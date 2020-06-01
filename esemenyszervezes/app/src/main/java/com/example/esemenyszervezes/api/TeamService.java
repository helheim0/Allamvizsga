package com.example.esemenyszervezes.api;

import com.example.esemenyszervezes.pojo.Result;
import com.example.esemenyszervezes.pojo.Team;
import com.example.esemenyszervezes.pojo.User;

import java.util.ArrayList;
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
    *   List teams
    */

    @GET("teams")
    Call<List<Team>> listTeams(
            @Header("Authorization") String token
            );
    /*
     *  Get a user's specific teams
     */
    @GET("teams/user/{id}")
    Call<List<Team>> listUserTeams(
            @Header("Authorization:close") String token,
            @Path("id") int id
    );

    /*
     *   Get ADMIN's teams
     */
    @GET("teams/admin/{id}")
    Call<List<Team>> listAdminTeams(
            @Header("Authorization") String token,
            @Path("id") int id
    );

    /*
     *  Get members of a team
     */
    @GET("teams/{id}/members")
    Call<List<User>> getMembers(
            @Header("Authorization:close") String token,
            @Path("id") int id
    );

    /*
     *  Join a team
     */
    @POST("teams/join/{teamId}/{userId}")
    Call<Result> joinTeam(
            @Header("Authorization") String token,
            @Path("teamId") int teamId,
            @Path("userId") int userId
    );
}
