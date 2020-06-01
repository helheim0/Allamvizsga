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
import android.widget.Toast;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.api.TeamService;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Result;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTeamActivity extends AppCompatActivity {
    private EditText mName, mDescription;
    private Button mButton;
    private static String token;
    private static int id;
    private static final String TAG = "CreateTeamActivity";
    private static final String PREFS_NAME = "LoginPrefs";
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = CreateTeamActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token", "");
        id = settings.getInt("id", 0);
        Log.d(TAG, "onResponse: id " + id + "token " + token);
        setupBottomNavigationView();

        //Retrieving resources
        mName = findViewById(R.id.team_name_et);
        mDescription = findViewById(R.id.team_desc_et);
        mButton = findViewById(R.id.create_team_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = mName.getText().toString().trim();
                final String description = mDescription.getText().toString().trim();

                //Checking input fields
                if (checkValidity(name, description)) {
                    Log.d(TAG, "onClick: called");
                    final ProgressDialog progressDialog = new ProgressDialog(CreateTeamActivity.this);
                    progressDialog.setMessage("Creating team...");
                    progressDialog.show();

                    TeamService service = RetrofitBuilder.getRetrofitInstance().create(TeamService.class);
                    Call<Result> call = service.createTeam("Bearer " + token, name, description, id);
                    Log.d(TAG, "onResponse: passed params" + name + description );
                    Log.d(TAG, "onClick: passed id" + id);
                    call.enqueue(new Callback<Result>() {

                        @Override
                        public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                Log.d(TAG, "successful: passed params" + name + description );
                                Log.d(TAG, "successful: passed id" + id);
                                Intent home = new Intent(CreateTeamActivity.this, TeamsActivity.class);
                                assert response.body() != null;
                                home.putExtra("token", response.body().getAccess_token());
                                home.putExtra("name", name);
                                startActivity(home);
                            } else {
                                Log.d(TAG, "wrong: passed params" + name + description );
                                Log.d(TAG, "wong: passed id" + id);
                                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            progressDialog.dismiss();
                            Log.d(TAG, "fail: passed params" + name + description );
                            Log.d(TAG, "fail: passed id" + id);
                            Log.d("Error", Objects.requireNonNull(t.getMessage()));
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    //Setting up the bottom navigation
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        BottomNavigationHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    //Validate input
    public boolean checkValidity(String name, String description){
        Log.d(TAG, "checkValidity: called");
        if(name.isEmpty()){
            mName.setError("Name cannot be empty");
            mName.requestFocus();
            return false;
        }
        else if(description.isEmpty()){
            mDescription.setError("Description cannot be empty!");
            mDescription.requestFocus();
            return false;
        }
        else return true;
    }
}
