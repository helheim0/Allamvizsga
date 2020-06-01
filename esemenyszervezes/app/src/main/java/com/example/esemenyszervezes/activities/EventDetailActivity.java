package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.MemberAdapter;
import com.example.esemenyszervezes.adapters.ParticipantAdapter;
import com.example.esemenyszervezes.adapters.UserAdapter;
import com.example.esemenyszervezes.api.ApiEvents;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.Event;
import com.example.esemenyszervezes.pojo.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity {
    private static final String TAG = "EventDetailActivity";
    public static final String EVENT_DETAIL = "EVENT_DETAIL";
    private Context mContext;
    private ListView listView, notGoingLV;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        listView = findViewById(R.id.going_RV);
        notGoingLV = findViewById(R.id.cant_go_RV);
        TextView mEventName = findViewById(R.id.main_event_title);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.my_event_detail_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        //get the data
        String name = getIntent().getStringExtra("name");

        //set the data
        mEventName.setText(name);
       /* Event mEvent1 = Objects.requireNonNull(getIntent().getExtras()).getParcelable(EVENT_DETAIL);
        if( mEvent1 != null)
        mEventName.setText(mEvent1.getName());
        Event mEvent = Objects.requireNonNull(getIntent().getExtras()).getParcelable(EVENT_DETAIL);
        assert mEvent != null;
        mEventName.setText(mEvent.getName());
        Intent intent = new Intent();
        Bundle data = getIntent().getExtras();
        assert data != null;
        mEvent = intent.getParcelableExtra(EVENT_DETAIL);
        assert mEvent != null;
        mEventName.setText(mEvent.getName());*/

        //loadData();
        going();
        notGoing();
    }

  //Show going
  public void going(){
      ApiEvents service = RetrofitBuilder.getRetrofitInstance().create(ApiEvents.class);

      Call<List<User>> call = service.goingParticipants(token, 2);
      call.enqueue(new Callback<List<User>>() {
          @Override
          public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
              if (response.isSuccessful()) {
                  Log.d(TAG, "onResponse: loadData called");
                  //Filling views with data
                  List<User> list = response.body();
                  UserAdapter adapter = new UserAdapter(EventDetailActivity.this, list);
                  listView.setAdapter(adapter);
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

    //Show going
    public void notGoing(){
        ApiEvents service = RetrofitBuilder.getRetrofitInstance().create(ApiEvents.class);
        Call<List<User>> call = service.notGoingParticipants(token, 2);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: loadData called");
                    //Filling views with data
                    List<User> notGoingList = response.body();
                    UserAdapter adapter = new UserAdapter(EventDetailActivity.this, notGoingList);
                    notGoingLV.setAdapter(adapter);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }
}
