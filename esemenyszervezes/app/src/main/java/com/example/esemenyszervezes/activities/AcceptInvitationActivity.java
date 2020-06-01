package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.api.TeamService;
import com.example.esemenyszervezes.pojo.Result;
import com.example.esemenyszervezes.pojo.Team;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptInvitationActivity extends AppCompatActivity{
private static final String TAG = "AcceptInvitationActivit";
private static final String PREFS_NAME = "LoginPrefs";
public static final String TEAM_DETAIL = "TEAM_DETAIL";
private String token;
private int id, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_invitation);

        //Retrieve token and id
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token", "");
        userId = settings.getInt("id", 0);
        Log.d(TAG, "onResponse: id " + id + "token " + token);

        //Getting resources
        TextView mName = findViewById(R.id.group_title);
        TextView mDescription = findViewById(R.id.descTxtView);

        //Getting data from intent
        Team mTeam = Objects.requireNonNull(getIntent().getExtras()).getParcelable(TEAM_DETAIL);
        assert mTeam != null;
        mName.setText(mTeam.getName());
        mDescription.setText(mTeam.getDescription());
        id = mTeam.getId();
    }

    //Join team
    public void accept(View view){
        TeamService service = RetrofitBuilder.getRetrofitInstance().create(TeamService.class);
        Call<Result> call = service.joinTeam("Bearer" + token, id, userId);
        Log.d(TAG, "onResponse: " + token + "team id: " + id + "user id: " +userId);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    Log.d(TAG, "onResponse: accept called");
                    //Data is sent to database, user is redirected to home page
                    Intent intent = new Intent(AcceptInvitationActivity.this, HomePageActivity.class);
                    startActivity(intent);
                }
                else{
                    Log.d(TAG, "onResponse: " + response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                Log.d("Response", "onFailure: " + t.toString());
            }
        });
    }

    //Decline join
    public void decline(View view){
        //Data is sent to database, user is redirected to home page
        Intent intent = new Intent(AcceptInvitationActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
}
