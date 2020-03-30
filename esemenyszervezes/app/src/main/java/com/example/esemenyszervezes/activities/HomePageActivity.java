package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.esemenyszervezes.fragments.EventFragment;
import com.example.esemenyszervezes.fragments.TeamFragment;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Event;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

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
       // getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new EventFragment()).commit();
        /*
        ApiService service = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
        Call<List<Event>> call = service.listEvents();

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
               if(response.isSuccessful()){
                   if(!eventList.isEmpty()){
                       eventList.clear();
                   }

                   eventList = response.body();
                   Log.d("TAG","Response = "+ eventList);
                   eventAdapter = new EventAdapter(HomePageActivity.this, eventList);
                   mEventRecyclerView.setAdapter(eventAdapter);
                   eventAdapter.notifyDataSetChanged();
               }
               else{
                   Toast.makeText(HomePageActivity.this, "No event", Toast.LENGTH_SHORT).show();
               }

            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d("response", "Hiba");
                Toast.makeText(HomePageActivity.this, "Hiba!!!!!!!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });*/
      //  new FetchDataTask().execute();
    }

  /*  private void init(){
        mEventRecyclerView = (RecyclerView) findViewById(R.id.eventRV);
        eventAdapter = new EventAdapter(getApplicationContext(), eventList);
        eventManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mEventRecyclerView.setHasFixedSize(true);
        mEventRecyclerView.setLayoutManager(eventManager);
        mEventRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mEventRecyclerView.setNestedScrollingEnabled(false);

        toolbar = getSupportActionBar();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }
*/

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
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("logged");
        editor.apply();
        finish();
    }
}
