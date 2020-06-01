package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.EventAdapter;
import com.example.esemenyszervezes.api.ApiEvents;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Event;
import com.example.esemenyszervezes.pojo.OnItemClickListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsActivity extends AppCompatActivity {
    private static final String TAG = "EventsActivity";
    private static final int ACTIVITY_NUM = 1;
    private static final String PREFS_NAME = "LoginPrefs";
    public static final String EVENT_DETAIL = "EVENT_DETAIL";
    private Context mContext = EventsActivity.this;
    private Event mEvent = new Event();
    private String token, name;
    private int id;
    private EventAdapter adapter;
    private RecyclerView recyclerView;
    private List<Event> eventList = new ArrayList<>();
    private List<Response> result;
    private TextView noEvents;
    private ImageView add, more;

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
        recyclerView = findViewById(R.id.event_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EventAdapter(mContext, eventList);
        recyclerView.setAdapter(adapter);

       adapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onAddClicked(int position) {
               addClick(position);
            }

            @Override
            public void onMoreClicked(int position) {
                moreClick(position);
            }
        });

        loadData();
        setButtons();
        setupBottomNavigationView();
    }

    public void addClick(int position){
        final Event addEvent = eventList.get(position);
        Intent i = new Intent(EventsActivity.this, EventInvitationActivity.class);
        i.putExtra("name", addEvent.getName());
        i.putExtra("description", addEvent.getDescription());
        i.putExtra("date", addEvent.getDate());
        i.putExtra("location", addEvent.getLocation());
        Log.d("hello", addEvent.getName());
        startActivity(i);
    }

    public void moreClick(int position){
        final Event event = eventList.get(position);
        Intent i = new Intent(EventsActivity.this, EventDetailActivity.class);
        i.putExtra("name", event.getName());
        Log.d("more hello", event.getName());
        startActivity(i);
    }

    public void setButtons() {
        add = findViewById(R.id.add_icon);
        more = findViewById(R.id.more_icon);
        Log.d(TAG, "setButtons: called");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: add onclicklistener");
                int position = Integer.parseInt(String.valueOf(v.getId()));
                addClick(position);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(String.valueOf(v.getId()));
                moreClick(position);
            }
        });
    }

    //Listing events
    private void loadData(){
        ApiEvents service = RetrofitBuilder.getRetrofitInstance().create(ApiEvents.class);
        Call<List<Event>> call = service.listEvents("Bearer" + token, id);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(@NonNull Call<List<Event>> call, @NonNull Response<List<Event>> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    Log.d(TAG, "onResponse: loadData called");
                    eventList = response.body();
                    adapter = new EventAdapter(mContext, eventList);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
                        @Override
                        public void onAddClicked(int position) {
                            addClick(position);
                        }

                        @Override
                        public void onMoreClicked(int position) {
                            moreClick(position);
                        }
                    });
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
        return super.onOptionsItemSelected(item);
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

}
