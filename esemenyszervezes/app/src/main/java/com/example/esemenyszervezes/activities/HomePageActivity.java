package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.EventAdapter;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Event;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePageActivity extends AppCompatActivity {
    private static final int ACTIVITY_NUM = 0;
    private static final String PREFS_NAME = "LoginPrefs";
    private Context mContext = HomePageActivity.this;
    private EventAdapter eventAdapter;
    private RecyclerView  mEventRecyclerView;
    private List<Event> eventList = new ArrayList<>();
    private BottomAppBar bottomAppBar;
    private ActionBar toolbar;
    private static final String TAG = "HomePageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setupBottomNavigationView();
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

    //Shared preferences log out
    public void logOut(View v){
       /*SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("logged");
        editor.apply();
        finish();*/
        Retrofit retrofit = RetrofitBuilder.getRetrofitInstance();
        ApiService loginServices = retrofit.create(ApiService.class);
        Call<Void> logout = loginServices.logout();

        logout.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());
                t.printStackTrace();
            }
        });
    }
}

