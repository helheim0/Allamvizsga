package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.EventAdapter;
import com.example.esemenyszervezes.api.ApiEvents;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Event;
import com.example.esemenyszervezes.pojo.SaveSharedPrefs;
import com.google.android.gms.common.api.Api;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsActivity extends AppCompatActivity {
    private static final String TAG = "EventsActivity";
    private static final int ACTIVITY_NUM = 1;
    private static final String PREFS_NAME = "LoginPrefs";
    public static final String EVENT_DETAIL = "EVENT_DETAIL";
    private Context mContext = EventsActivity.this;
    private Event mEvent;
    private String token, name;
    private int id;
    private EventAdapter adapter;
    private RecyclerView recyclerView;
    private List<Event> eventList = new ArrayList<>();
    private List<Response> result;
    private TextView noEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        //Get token and id from shared preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token", "");
        id = settings.getInt("id", 0);
        Log.d(TAG, "onResponse: id " + id + "token " + token);

        noEvents = findViewById(R.id.noEvents);
        recyclerView = (RecyclerView) findViewById(R.id.event_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EventAdapter(mContext, eventList);
        recyclerView.setAdapter(adapter);

        loadData();
        setupBottomNavigationView();
    }

    private void loadData(){
        ApiEvents service = RetrofitBuilder.getRetrofitInstance().create(ApiEvents.class);
        Call<List<Event>> call = service.listEvents("Bearer" + token, id);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(@NonNull Call<List<Event>> call, @NonNull Response<List<Event>> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    Log.d(TAG, "onResponse: loadData called");
                /*  String token = response.headers().get("Authorization");
                    if (token != null) {
                        Log.e("tokenTAG", "Token : " + token);
                        sendToken = token;
                    }*/

                    eventList = response.body();
                    adapter = new EventAdapter(mContext, eventList);

                    recyclerView.setAdapter(adapter);
                }
                else if(response.body() == null){
                    noEvents.setVisibility(View.VISIBLE);
                }
                else{
                    Log.d(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Event>> call, @NonNull Throwable t) {
                Log.d("Response", "onFailure: " + t.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
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
