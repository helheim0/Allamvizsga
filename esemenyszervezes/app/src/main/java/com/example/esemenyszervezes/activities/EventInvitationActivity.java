package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.EventAdapter;
import com.example.esemenyszervezes.api.ApiEvents;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Event;
import com.example.esemenyszervezes.pojo.Result;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventInvitationActivity extends AppCompatActivity {
    private static final String TAG = "EventInvitationActivity";
    private static final String PREFS_NAME = "LoginPrefs";
    public static final String EVENT_DETAIL = "EVENT_DETAIL";
    private Context mContext;
    private TextView mName, mDescription, mLocation, mDate;
    private String token;
    private int id, userId;
    private Event mEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_invitation);

        //Retrieving token and user id
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token", "");
        id = settings.getInt("id", 0);
        Log.d(TAG, "onResponse: id " + id + "token " + token);

        //Getting resources
        mName = findViewById(R.id.eventName);
        mDescription = findViewById(R.id.description);
        mLocation = findViewById(R.id.location);
        mDate = findViewById(R.id.date);
//get the data
        String name = getIntent().getStringExtra("name");
        String date = getIntent().getStringExtra("date");
        String description = getIntent().getStringExtra("description");
        String location = getIntent().getStringExtra("location");

//set the data
        mName.setText(name);
        mDescription.setText(description);
        mLocation.setText(location);
        mDate.setText(date);
      /*  mEvent = Objects.requireNonNull(getIntent().getExtras()).getParcelable(EVENT_DETAIL);
       if(mEvent != null){
        mName.setText(mEvent.getName());
        mDescription.setText(mEvent.getDescription());
        mLocation.setText(mEvent.getLocation());
        mDate.setText(mEvent.getDate());
       }
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            Event mEvent = bundle.getParcelable(EVENT_DETAIL);
            assert mEvent != null;
            mName.setText(mEvent.getName());
            mDescription.setText(mEvent.getDescription());
            mLocation.setText(mEvent.getLocation());
            mDate.setText(mEvent.getDate());
        }*/
        setupBottomNavigationView();
        loadData();
    }

    private void loadData(){
        ApiEvents service = RetrofitBuilder.getRetrofitInstance().create(ApiEvents.class);
        Call<Event> call = service.showEventInvitation("Bearer" + token, id);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(@NonNull Call<Event> call, @NonNull Response<Event> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    Log.d(TAG, "onResponse: loadData called");
                    mName.setText(response.body().getName());
                    mDescription.setText(response.body().getDescription());
                    mDate.setText(response.body().getDate());
                    mLocation.setText(response.body().getLocation());
                }
                else{
                    Log.d(TAG, "onResponse: " + response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Event> call, @NonNull Throwable t) {
                Log.d("Response", "onFailure: " + t.toString());
            }
        });
    }

    //Accept invitation
    public void acceptInvitation(View view){
        ApiEvents service = RetrofitBuilder.getRetrofitInstance().create(ApiEvents.class);
        Call<Result> call = service.acceptEvent("Bearer" + 1, 2, 7);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    Log.d(TAG, "onResponse: accept called");
                    Intent intent = new Intent(EventInvitationActivity.this, EventDetailActivity.class);
                    startActivity(intent);
                }
                else{
                    Log.d(TAG, "onResponse: " + response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                Log.d("Response", "onFailure: " + t.toString());
            }
        });
    }

    //Decline invitation
    public void declineInvitation(View view){
        ApiEvents service = RetrofitBuilder.getRetrofitInstance().create(ApiEvents.class);
        Call<Result> call = service.declineEvent("Bearer" + 1, 2, 7);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    Log.d(TAG, "onResponse: decline called");
                    Intent intent = new Intent(EventInvitationActivity.this, HomePageActivity.class);
                    startActivity(intent);
                }
                else{
                    Log.d(TAG, "onResponse: " + response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
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
    }
}
