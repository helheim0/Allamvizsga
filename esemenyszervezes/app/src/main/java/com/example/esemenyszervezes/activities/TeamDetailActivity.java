package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.MemberAdapter;
import com.example.esemenyszervezes.adapters.TeamAdapter;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Team;
import com.example.esemenyszervezes.pojo.User;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamDetailActivity extends AppCompatActivity {
    private static final String TAG = "TeamDetailActivity";
    private Context mContext;
    private ListView listView;
    private MemberAdapter adapter;
    private List<User> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);

        setupBottomNavigationView();
        listView = findViewById(R.id.team_member_listview);

        loadData();
    }

    public void loadData(){
        ApiService service = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
        Call<List<User>> call = service.listTeamMembers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: loadData called");
                    // String token = response.headers().get("Authorization");
                    memberList = response.body();
                    adapter = new MemberAdapter(mContext, memberList);
                    listView.setAdapter(adapter);
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

    //Setting up bottom navigation
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        BottomNavigationHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(mContext, bottomNavigationViewEx);
        /*Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);*/
    }
}
