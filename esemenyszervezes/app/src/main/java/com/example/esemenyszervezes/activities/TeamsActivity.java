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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.TeamAdapter;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.api.TeamService;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.OnItemClickListener;
import com.example.esemenyszervezes.pojo.Team;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsActivity extends AppCompatActivity implements OnItemClickListener {
    private static final String TAG = "TeamsActivity";
    private static final int ACTIVITY_NUM = 3;
    private static final String PREFS_NAME = "LoginPrefs";
    private Context mContext = TeamsActivity.this;
    private static  String token;
    private int activityNum = 1;
    private static int id, teamId, role;
    private TeamAdapter adapter;
    private RecyclerView recyclerView;
    private List<Team> teamList;
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
        adapter = new TeamAdapter(teamList, mContext);
        recyclerView.setAdapter(adapter);

        setupBottomNavigationView();
        loadData();
    }

    public void loadData(){
        TeamService service = RetrofitBuilder.getRetrofitInstance().create(TeamService.class);
        Call<List<Team>> call = service.listAdminTeams("Bearer "+token, id);
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(@NonNull Call<List<Team>> call, @NonNull Response<List<Team>> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: loadData called");
                    teamList = response.body();
                    adapter = new TeamAdapter(teamList, mContext);
                    recyclerView.setAdapter(adapter);
                    adapter.setClickListener(TeamsActivity.this);
                    adapter.notifyDataSetChanged();
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
    public void onClick(View view, int position) {
      // The onClick implementation of the RecyclerView item click
        final Team team = teamList.get(position);
        Intent i = new Intent(this, TeamAdminActivity.class);
        i.putExtra("name", team.getName());
        i.putExtra("desc", team.getDescription());
        i.putExtra("code", team.getCode());
        i.putExtra("teamId", team.getId());
        Log.i("hello", team.getName());
        startActivity(i);
    }
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_teams, menu);
        return true;
    }*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.edit:
                int position = 0;
                 final Team team = teamList.get(position);
                 Intent i = new Intent(TeamsActivity.this, TeamAdminActivity.class);
                 i.putExtra("name", team.getName());
                 i.putExtra("desc", team.getDescription());
                 i.putExtra("teamId", team.getId());
                  Log.i("hello", team.getName());
                  adapter.notifyDataSetChanged();
                return true;
            case R.id.delete:
                teamList.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

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
