package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.api.ApiEvents;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Event;
import com.example.esemenyszervezes.pojo.Result;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "CreateEventActivity";
    private static final String PREFS_NAME = "LoginPrefs";
    private Context mContext;
    private static int id;
    private static String token;
    private EditText mName, mDate, mLocation, mDescription;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token", "");
        id = settings.getInt("id", 0);
        setupBottomNavigationView();

        mName = findViewById(R.id.eventName_et);
        mDescription = findViewById(R.id.desc_et);
        mLocation = findViewById(R.id.location_et);
        mDate = findViewById(R.id.date_et);
        mButton = findViewById(R.id.create_button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = mName.getText().toString().trim();
        String description = mDescription.getText().toString().trim();
        String date = mDate.getText().toString().trim();
        String location = mLocation.getText().toString().trim();

        if(checkValidity(name, date, description, location)){

            final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
            progressDialog.setMessage("Creating event...");
            progressDialog.show();
            Log.d(TAG, "onClick: called");

            ApiEvents service = RetrofitBuilder.getRetrofitInstance().create(ApiEvents.class);

            Call<Result> call = service.createEvent("Bearer "+token, name, description, date, location, id);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            Log.d("Error", "Event created");
                            assert response.body() != null;
                            token = response.body().getAccess_token();
                            id = response.body().getUserId();
                            Toast.makeText(getApplicationContext(), "Event created successfully!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("Error", t.getMessage());
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
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
}
