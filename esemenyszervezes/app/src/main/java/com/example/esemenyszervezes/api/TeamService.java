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

public interface TeamService {
    /*
     *   Create new team
     */
    @FormUrlEncoded
    @Multipart
    @POST("teams")
    Call<Result> createTeam(
            @Header("Authorization") String token,
            @Field("name") String name
            //@Field("image") String image
            //@Part MultipartBody.Part image
            //  @Field("admin_id") String admin
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

}
