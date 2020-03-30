package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.EventAdapter;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Event;
import com.example.esemenyszervezes.pojo.EventList;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EventsActivity extends AppCompatActivity{
    private static final String TAG = "EventsActivity";
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = EventsActivity.this;
    private String token;
    private EventAdapter adapter;
    private RecyclerView recyclerView;
    private List<Event>  eventList = new ArrayList<>();
    private TextView name;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        recyclerView = (RecyclerView) findViewById(R.id.event_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EventAdapter(mContext, eventList);
        recyclerView.setAdapter(adapter);
        loadData();
        setupBottomNavigationView();
    }

    private void loadData(){
        ApiService service = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
        Call<Event> call = service.listEvents();
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(@NonNull Call<Event> call, @NonNull Response<Event> response) {
                if (response.isSuccessful() && response.body()!=null) {
                 //  ArrayList<Event> events = response.body();
                 //  recyclerView.setAdapter(new EventAdapter(EventsActivity.this, events));
                }
                    /*String json = response.body().toString();
                    Log.d(TAG, "onResponse: json: " + json);
                    JSONArray array = new JSONArray(response.body().toString());
                   for (int i=0; i<array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        Event data = new Event(object.getString("name"),object.getString("date"),
                                object.getString("location"),object.getString("description"),object.getString("image"));
                        eventList.add(data);
                        adapter.notifyDataSetChanged();
                    }*/
            }

            @Override
            public void onFailure(@NonNull Call<Event> call, @NonNull Throwable t) {
                Log.d("Response", "onFailure: " + t.toString());
            }
        });
    }
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
