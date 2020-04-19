package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.MemberAdapter;
import com.example.esemenyszervezes.adapters.TeamAdapter;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.api.TeamService;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Team;
import com.example.esemenyszervezes.pojo.User;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamDetailActivity extends AppCompatActivity {
    private static final String TAG = "TeamDetailActivity";
    public static final String TEAM_DETAIL = "TEAM_DETAIL";
    private static final String PREFS_NAME = "LoginPrefs";
    private Context mContext;
    private String token;
    private int id;
    private Team mTeam;
    private ListView listView;
    private ListView memberView;
    private MemberAdapter adapter;
    private TeamAdapter teamAdapter;
    private List<User> memberList;
    private List<Team> membersList;

    private TextView mName, mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);

        setupBottomNavigationView();
        //Getting token and id from shared preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token", "");
        id = settings.getInt("id", 0);
        Log.d(TAG, "onResponse: id " + id + "token " + token);

        //Getting other resources
        mName = findViewById(R.id.main_team_title);
        mDescription = findViewById(R.id.main_team_description);
        listView = findViewById(R.id.team_member_recyclerview);

        Bundle data = getIntent().getExtras();
        assert data != null;
        Intent intent = new Intent();
        mTeam = intent.getParcelableExtra(TEAM_DETAIL);
        assert mTeam != null;
        mName.setText(mTeam.getName());
        mDescription.setText(mTeam.getDescription());
       // mTeam = (Team) Objects.requireNonNull(getIntent().getExtras()).getSerializable(TEAM_DETAIL);

        //loadData();
        getMembers();
    }

    //Load data
   /* public void loadData(){
        TeamService service = RetrofitBuilder.getRetrofitInstance().create(TeamService.class);
        Call<List<Team>> call = service.listUserTeams("Bearer" + token, id);
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(@NonNull Call<List<Team>> call, @NonNull Response<List<Team>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: loadData called");
                    //Filling views with data
                    mName.setText(mTeam.getName());
                    mDescription.setText(mTeam.getDescription());
                    membersList = response.body();
                    teamAdapter = new TeamAdapter(mContext, membersList);
                    listView.setAdapter(teamAdapter);
                }
                else{
                    Log.d(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Team>> call, @NonNull Throwable t) {
                Log.d("Response", "onFailure: " + t.toString());
            }
        });
    }
*/
    //Show members
    public void getMembers(){
        TeamService service = RetrofitBuilder.getRetrofitInstance().create(TeamService.class);
        Call<List<Team>> call = service.getMembers(token, id);
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(@NonNull Call<List<Team>> call, @NonNull Response<List<Team>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: loadData called");
                    // String token = response.headers().get("Authorization");
                    //Filling views with data
                    membersList = response.body();
                    adapter = new MemberAdapter(mContext, memberList);
                    memberView.setAdapter(adapter);
                }
                else{
                    Log.d(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Team>> call, @NonNull Throwable t) {
                Log.d("Response", "onFailure: " + t.toString());
            }
        });
    }

    //Setting up bottom navigation
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        BottomNavigationHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(mContext, bottomNavigationViewEx);
    }
}
