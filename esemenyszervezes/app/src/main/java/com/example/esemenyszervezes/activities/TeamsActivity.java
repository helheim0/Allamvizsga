package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.TeamAdapter;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.api.TeamService;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.SaveSharedPrefs;
import com.example.esemenyszervezes.pojo.Team;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsActivity extends AppCompatActivity{
    private static final String TAG = "TeamsActivity";
    private static final int ACTIVITY_NUM = 3;
    private static final String PREFS_NAME = "LoginPrefs";
    private Context mContext = TeamsActivity.this;
    private static  String token;
    private static int id;
    private TeamAdapter adapter;
    private RecyclerView recyclerView;
    private List<Team> teamList;
    private List<Response> result;
    private ArrayList<TeamAdapter> teams;
    private TextView noTeams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token", "");
        id = settings.getInt("id", 0);
        Log.d(TAG, "onResponse: id " + id + "token " + token);

        //Initializing the list, recyclerview and adapter
        noTeams = findViewById(R.id.noTeams);
        teamList = new ArrayList<>();
        recyclerView = findViewById(R.id.team_recyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TeamAdapter(mContext, teamList);
        recyclerView.setAdapter(adapter);

        setupBottomNavigationView();
        loadData();
    }

    public void loadData(){
        Log.d(TAG, "onResponse: loadData called");
        TeamService service = RetrofitBuilder.getRetrofitInstance().create(TeamService.class);
        Call<List<Team>> call = service.listUserTeams("Bearer "+token, id);
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(@NonNull Call<List<Team>> call, @NonNull Response<List<Team>> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: successful loadData called");
                    // String token = response.headers().get("Authorization");
                    teamList = response.body();
                    adapter = new TeamAdapter(mContext, teamList);

                    recyclerView.setAdapter(adapter);
                }
                else if(response.body() == null){
                    Log.d(TAG, "onResponse: no teams text view called");
                    noTeams.setVisibility(View.VISIBLE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_teams, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
    //Setting up bottom navigation
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
