package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.TeamAdapter;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Team;
import com.example.esemenyszervezes.pojo.TeamList;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsActivity extends AppCompatActivity{
    private static final String TAG = "TeamsActivity";
    private static final int ACTIVITY_NUM = 3;
    private Context mContext = TeamsActivity.this;
    private static  String token;
    private List<Team> teamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        teamList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.team_recyclerview);
        TeamAdapter adapter = new TeamAdapter(this, teamList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TeamsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        setupBottomNavigationView();
        loadData();
    }

    public void loadData(){
        ApiService service = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
        Call<TeamList> call = service.listTeams(token);
        call.enqueue(new Callback<TeamList>() {
            @Override
            public void onResponse(@NonNull Call<TeamList> call, @NonNull Response<TeamList> response) {
                if (response.isSuccessful()) {
                    String token = response.headers().get("Authorization");
                }
            }

            @Override
            public void onFailure(@NonNull Call<TeamList> call, @NonNull Throwable t) {
                Log.d("Response", "onFailure: " + t.toString());
            }
        });
    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        BottomNavigationHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
