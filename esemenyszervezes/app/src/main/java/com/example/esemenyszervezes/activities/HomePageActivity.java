package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.Toast;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.EventAdapter;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Event;
import com.example.esemenyszervezes.pojo.SaveSharedPrefs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePageActivity extends AppCompatActivity {
    private static final int ACTIVITY_NUM = 0;
    private static final String PREFS_NAME = "LoginPrefs";
    public static final String LOGGED_IN_PREF = "logged_in_status";
    private Context mContext = HomePageActivity.this;
    private String token;
    private int id;
    private EventAdapter eventAdapter;
    private RecyclerView  mEventRecyclerView;
    private List<Event> eventList = new ArrayList<>();
    private BottomAppBar bottomAppBar;
    private Toolbar toolbar;
    private static final String TAG = "HomePageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        /*Intent intent = getIntent();
        token = intent.getStringExtra("token");
        id = intent.getIntExtra("id", 0);
        id = SaveSharedPrefs.getId(mContext);
        token = SaveSharedPrefs.getToken(mContext);*/
        setupBottomNavigationView();
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( HomePageActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);

            }
        });

        //  getData();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token", "");
        id = settings.getInt("id", 0);
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }
    //Toolbar onclick
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            logOut();
            Log.d(TAG, "onOptionsItemSelected: logout called");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Shared preferences id + token
    public void getData(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        id = SaveSharedPrefs.getId(mContext);
        token = SaveSharedPrefs.getToken(mContext);
        editor.apply();
    }

    //Shared preferences log out
    public void logOut(){
        SharedPreferences settings = this.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        //editor.putBoolean(LOGGED_IN_PREF, false);
        editor.clear();
        editor.apply();
        Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

      /*  Retrofit retrofit = RetrofitBuilder.getRetrofitInstance();
        ApiService loginServices = retrofit.create(ApiService.class);
        Call<Void> logout = loginServices.logout();
        logout.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(HomePageActivity.this, "Something went wrong",
                    Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());
                t.printStackTrace();
            }
        });*/
    }
}

