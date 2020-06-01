package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.SearchAdapter;
import com.example.esemenyszervezes.adapters.TeamDetailAdapter;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.api.TeamService;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.OnItemClickListener;
import com.example.esemenyszervezes.pojo.Team;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity implements OnItemClickListener,  SearchAdapter.SelectedTeam {
    private static final int ACTIVITY_NUM = 0;
    private static final String PREFS_NAME = "LoginPrefs";
    private Context mContext = HomePageActivity.this;
    private String token;
    private int id;
    private TeamDetailAdapter adapter;
    private RecyclerView recyclerView, searchRV;
    private List<Team> teamList;
    private TextView noTeams;
    private static final String TAG = "HomePageActivity";
    SearchAdapter searchAdapter;
    List<Team> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Shared preferences + toolbar
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token", "");
        id = settings.getInt("id", 0);
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        //Initializing the list, recyclerview and adapter
        teamList = new ArrayList<>();
        myList = new ArrayList<>();
        recyclerView = findViewById(R.id.userteam_recyclerview);
        noTeams = findViewById(R.id.noUserTeams);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TeamDetailAdapter(mContext, teamList);
        recyclerView.setAdapter(adapter);

        //Search
        searchRV = findViewById(R.id.recyclerView);
        searchRV.setHasFixedSize(true);
        searchRV.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(myList, this, HomePageActivity.this);

        setupBottomNavigationView();

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("APP ID") // Required for Analytics.
                .setProjectId("PROJECT ID") // Required for Firebase Installations.
                .setApiKey("GOOGLE API KEY") // Required for Auth.
                .build();

        // FirebaseApp.initializeApp(this, options, "FIREBASE APP NAME");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = Objects.requireNonNull(task.getResult()).getToken();
                        String msg = getString(R.string.fcm_token, token);
                        Log.d(TAG, msg);
                        Toast.makeText(HomePageActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }
                });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = Objects.requireNonNull(task.getResult()).getToken();

                        // Log and toast
                        String msg = getString(R.string.fcm_token, token);
                        Log.d(TAG, msg);
                        Toast.makeText(HomePageActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        searchTeams("teams", "");
        loadData();
    }

    //List user teams
    public void loadData(){
        Log.d(TAG, "onResponse: loadData called");
        TeamService service = RetrofitBuilder.getRetrofitInstance().create(TeamService.class);
        Call<List<Team>> call = service.listUserTeams("Bearer "+token, id);
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(@NonNull Call<List<Team>> call, @NonNull Response<List<Team>> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: successful team loadData called");
                    teamList = response.body();
                    adapter = new TeamDetailAdapter(mContext, teamList);
                    recyclerView.setAdapter(adapter);
                    adapter.setClickListener(HomePageActivity.this);
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

    //Setting up bottom navigation
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottom_navigation);
        BottomNavigationHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu with items using MenuInflater
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        // Initialize menu item search bar with id and take its object
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(false);
        searchView.setClickable(true);
        // attach setOnQueryTextListener  to search view defined above
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    // Override onQueryTextSubmit method which is called when submitted query is searched
                    @Override
                    public boolean onQueryTextSubmit(String query)
                    {
                        // If the list contains the search query then filter the adapter using the filter method with the query as its argument
                        searchTeams("teams", query);
                        return false;
                    }
                    // This method is overridden to filter  the adapter according to a search query  when the user is typing search
                    @Override
                    public boolean onQueryTextChange(String newText)
                    {
                        searchTeams("teams", newText);
                        return false;
                    }
                });

            searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Team team = (Team) v.getTag();
                Intent i = new Intent(HomePageActivity.this, AcceptInvitationActivity.class);
                i.putExtra("name", team.getName());
                i.putExtra("desc", team.getDescription());
                i.putExtra("teamId", team.getId());
                Log.i("hello", team.getName());
                Log.d(TAG, "onClick: search view item onclick called");
                startActivity(i);
            }
        });
        return true;
    }

    //Toolbar onclick
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            logOut();
            Log.d(TAG, "onOptionsItemSelected: logout called");
            return true;
        }
        else if(id == R.id.action_search){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Search for teams
    public void searchTeams(String type, String query){
        Log.d(TAG, "onResponse: loadData called");
        ApiService service = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
        Call<List<Team>> call = service.searchTeams("Bearer "+ token, query);
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(@NonNull Call<List<Team>> call, @NonNull Response<List<Team>> response) {
                try {
                    if (response.isSuccessful()){
                        Log.d(TAG, "onResponse: successful loadData called");
                        myList = response.body();
                        searchAdapter = new SearchAdapter(myList, HomePageActivity.this, HomePageActivity.this);
                        searchRV.setAdapter(searchAdapter);
                        searchAdapter.notifyDataSetChanged();
                        searchAdapter.swapData(myList);
                    }
                    else{
                        Log.d(TAG, "onResponse: " + response.body());
                    }
                }
                catch (Exception e){
                    Toast.makeText(HomePageActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Team>> call, @NonNull Throwable t) {
                Log.d("Response", "onFailure: " + t.toString());
                Toast.makeText(HomePageActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //User teams onclick -> pass data to detail activity
    @Override
    public void onClick(View view, int position) {
        final Team team = teamList.get(position);
        Intent i = new Intent(this, TeamDetailActivity.class);
        i.putExtra("name", team.getName());
        i.putExtra("desc", team.getDescription());
        i.putExtra("teamId", team.getId());
        Log.i("hello", team.getName());
        startActivity(i);
    }

    //Shared preferences log out
    public void logOut(){
        SharedPreferences settings = this.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //Search onclick redirecting to accept invitation activity
    @Override
    public void selectedTeam(Team teamModel) {
        startActivity(new Intent(HomePageActivity.this, AcceptInvitationActivity.class).putExtra("data", teamModel));
    }
}

