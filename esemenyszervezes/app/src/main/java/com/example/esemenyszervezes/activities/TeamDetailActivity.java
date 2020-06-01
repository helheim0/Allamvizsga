package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.MemberAdapter;
import com.example.esemenyszervezes.adapters.TeamAdapter;
import com.example.esemenyszervezes.adapters.TeamDetailAdapter;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.api.TeamService;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Team;
import com.example.esemenyszervezes.pojo.User;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamDetailActivity extends AppCompatActivity {
    private static final String TAG = "TeamDetailActivity";
    public static final String TEAM_DETAIL = "TEAM_DETAIL";
    private static final String PREFS_NAME = "LoginPrefs";
    private Context mContext;
    private String token;
    private int id, teamId;
    private Team mTeam;
    private ListView listView;
    private MemberAdapter adapter;
    private TeamAdapter teamAdapter;
    private List<User> memberList;
    private TextView noMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);

        //Getting token and id from shared preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token", "");
        id = settings.getInt("id", 0);
        Log.d(TAG, "onResponse: id " + id + "token " + token);

        //Getting other resources
        TextView mName = findViewById(R.id.main_team_title);
        TextView  mDescription = findViewById(R.id.main_team_description);
        noMembers = findViewById(R.id.noMembers);
        listView = findViewById(R.id.team_member_recyclerview);
        Log.d(TAG, "onCreate: team id" + teamId);
        memberList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            Team mTeam = bundle.getParcelable(TEAM_DETAIL);
            assert mTeam != null;
            teamId = mTeam.getId();
            mName.setText(mTeam.getName());
            mDescription.setText(mTeam.getDescription());
        }

        //Toolbar
        Toolbar toolbar = findViewById(R.id.my_team_detail_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        getMembers();
    }

    //Show members
    public void getMembers(){
        TeamService service = RetrofitBuilder.getRetrofitInstance().create(TeamService.class);

        Call<List<User>> call = service.getMembers(token, teamId);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: loadData called");
                    // String token = response.headers().get("Authorization");
                    //Filling views with data
                    List<User> list = response.body();


                    MemberAdapter adapter = new MemberAdapter(TeamDetailActivity.this, list);
                    listView.setAdapter(adapter);
                }
                else if(response.body() == null){
                    Log.d(TAG, "onResponse: no members text view called");
                    noMembers.setVisibility(View.VISIBLE);
                }
                else{
                    Log.d(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.d("Response", "onFailure: " + t.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }
}
