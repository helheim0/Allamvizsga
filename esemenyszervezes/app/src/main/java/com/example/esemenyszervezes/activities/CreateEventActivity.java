package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.api.ApiEvents;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.Event;
import com.example.esemenyszervezes.pojo.Result;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "CreateEventActivity";
    private static final String PREFS_NAME = "LoginPrefs";
    public static final String EVENT_DETAIL = "EVENT_DETAIL";
    private static int id, teamId;
    private static String token;
    private EditText mName, mDate, mLocation, mDescription;
    private Event mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token", "");
        id = settings.getInt("id", 0);
        Intent intent = getIntent();
        teamId = intent.getIntExtra("team_id", 0);

        mName = findViewById(R.id.eventName_et);
        mDescription = findViewById(R.id.desc_et);
        mLocation = findViewById(R.id.location_et);
        mDate = findViewById(R.id.date_et);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.my_event_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        //Create button
        Button mButton = findViewById(R.id.create_button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        final String name = mName.getText().toString().trim();
        final String description = mDescription.getText().toString().trim();
        final String date = mDate.getText().toString().trim();
        final String location = mLocation.getText().toString().trim();

        if(checkValidity(name, date, description, location)){
            final ProgressDialog progressDialog = new ProgressDialog(CreateEventActivity.this);
            progressDialog.setMessage("Creating event...");
            progressDialog.show();
            Log.d(TAG, "onClick: called");

            ApiEvents service = RetrofitBuilder.getRetrofitInstance().create(ApiEvents.class);
            Call<Result> call = service.createEvent("Bearer "+token, name, date, location, description, id, teamId);
            Log.d(TAG, "onClick: params" + token + name + date + location + description + id);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            Log.d("Error", "Event created");
                            Toast.makeText(CreateEventActivity.this, "Event created successfully!", Toast.LENGTH_LONG).show();
                            Intent home = new Intent(CreateEventActivity.this, EventsActivity.class);
                            startActivity(home);
                        }
                        else {
                            Toast.makeText(CreateEventActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Result> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("Error", Objects.requireNonNull(t.getMessage()));
                        Toast.makeText(CreateEventActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
    }

    //Validate input
    public boolean checkValidity(String name, String date, String description, String location){

        if(name.isEmpty()){
            mName.setError("Name cannot be empty");
            mName.requestFocus();
            return false;
        }

        else if(date.isEmpty()){
            mDate.setError("Date cannot be empty!");
            mDate.requestFocus();
            return false;
        }

        else if(description.isEmpty()){
            mDescription.setError("Description cannot be empty!");
            mDescription.requestFocus();
            return false;
        }

        else if(location.isEmpty()){
            mLocation.setError("Location cannot be empty!");
            mLocation.requestFocus();
            return false;
        }
        else
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
