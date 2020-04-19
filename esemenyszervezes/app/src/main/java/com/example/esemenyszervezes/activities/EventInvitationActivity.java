package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventInvitationActivity extends AppCompatActivity {
    private static final String TAG = "EventInvitationActivity";
    private Context mContext;
    private TextView mName, mDescription, mLocation, mDate;
    private Button go, noGo;
    private String token;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_invitation);

        //Getting resources
        mName = findViewById(R.id.eventName);
        mDescription = findViewById(R.id.description);
        mLocation = findViewById(R.id.location);
        mDate = findViewById(R.id.date);
        go = findViewById(R.id.going);
        noGo = findViewById(R.id.cantGo);

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
    private void acceptInvitation(View v){

    }

    //Decline invitation
    private void declineInvitation(View v){

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
