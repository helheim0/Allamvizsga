package com.example.esemenyszervezes.pojo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.activities.CreateEventActivity;
import com.example.esemenyszervezes.activities.CreateTeamActivity;
import com.example.esemenyszervezes.activities.EventsActivity;
import com.example.esemenyszervezes.activities.HomePageActivity;
import com.example.esemenyszervezes.activities.TeamsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationHelper {
    private static final String TAG = "BottomNavigationHelper";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        context.startActivity(new Intent(context, HomePageActivity.class));
                        break;
                    case R.id.profile:
                        context.startActivity(new Intent(context, EventsActivity.class));
                        break;
                    case R.id.add:
                        context.startActivity(new Intent(context, CreateEventActivity.class));
                        break;
                    case R.id.teams:
                        context.startActivity(new Intent(context, TeamsActivity.class));
                        break;
                }
                return false;
            }
        });
    }

}
