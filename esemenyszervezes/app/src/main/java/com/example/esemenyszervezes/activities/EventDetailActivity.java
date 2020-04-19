package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.ParticipantAdapter;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Event;
import com.example.esemenyszervezes.pojo.User;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity {
    private static final String TAG = "EventDetailActivity";
    public static final String EVENT_DETAIL = "EVENT_DETAIL";
    private Context mContext;
    private ListView listView;
    private ParticipantAdapter adapter;
    private List<User> participantList;
    private Event mEvent;
    private TextView mEventName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        listView = findViewById(R.id.going_RV);
        mEventName = findViewById(R.id.main_event_title);

        setupBottomNavigationView();
       /* ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/
        Intent intent = new Intent();
        Bundle data = getIntent().getExtras();
        assert data != null;
        mEvent = intent.getParcelableExtra(EVENT_DETAIL);
        assert mEvent != null;
        mEventName.setText(mEvent.getName());
       /* mEvent = (Event)getIntent().getExtras().getSerializable(EVENT_DETAIL);
        mEventName.setText(mEvent.getName());*/
        loadData();
    }

  /*  public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MyActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }*/
    public void loadData(){
        ApiService service = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
        Call<List<User>> call = service.listTeamMembers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    //if participate == true
                    Log.d(TAG, "onResponse: loadData called");
                    // String token = response.headers().get("Authorization");
                    participantList = response.body();
                    adapter = new ParticipantAdapter(mContext, participantList);
                    listView.setAdapter(adapter);
                    //if participate == false
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
