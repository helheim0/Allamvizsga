package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.EmailResponse;
import com.example.esemenyszervezes.pojo.Result;
import com.example.esemenyszervezes.pojo.Team;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamAdminActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TeamAdminActivity";
    private static final String PREFS_NAME = "LoginPrefs";
    private Context mContext = TeamAdminActivity.this;
    private TextView teamName, teamCode;
    private EditText emailField;
    private String token, code = " ";
    private int id, teamId;
    private ListView listView;
    public static final String TEAM_DETAIL = "TEAM_DETAIL";
    private Team mTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_admin);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token", "");
        Log.d(TAG, "onResponse: " + token);

        teamName = findViewById(R.id.team_title);
        teamCode = findViewById(R.id.team_code);
        emailField = findViewById(R.id.search_et);
        Button invite = findViewById(R.id.invite_btn);
        invite.setOnClickListener(this);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.my_admin_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mTeam = Objects.requireNonNull(getIntent().getExtras()).getParcelable(TEAM_DETAIL);
        assert mTeam != null;
        teamName.setText(mTeam.getName());
        teamCode.setText(mTeam.getCode());



        //FAB onclick handling + creating event
        FloatingActionButton floatingActionButton = findViewById(R.id.create_event);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Log.d(TAG, "onClick: FAB clicked");
              Intent intent = new Intent(TeamAdminActivity.this, CreateEventActivity.class);
              teamId = mTeam.getId();
              intent.putExtra("team_id", teamId);
                Log.d(TAG, "onClick: team id" + teamId);
             startActivity(intent);
            }
        });

        setupBottomNavigationView();

    }

    //Setting up bottom navigation
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottom_navigation);
        BottomNavigationHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(mContext, bottomNavigationViewEx);
    }

    //Invite onclick
    @Override
    public void onClick(View v) {
        Toast.makeText(TeamAdminActivity.this, "invite clicked", Toast.LENGTH_LONG).show();

        String email = emailField.getText().toString();
        String name = teamName.getText().toString();

        String code = teamCode.getText().toString();
        ApiService service = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
        Call<Result> call = service.inviteUser("Bearer "+token, email, name, "sgffadg");
        Log.d(TAG, "onResponse: name " + name + "email" + email + "code" + code);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                try {
                    if (response.isSuccessful()){
                        Log.d(TAG, "onResponse: successful loadData called");
                        assert response.body() != null;
                        Toast.makeText(TeamAdminActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                    }
                    else{
                        assert response.body() != null;
                        Toast.makeText(TeamAdminActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: " + response.body());
                    }
                }
                catch (Exception e){
                    Log.d(TAG, "onResponse: " + e.getMessage());
                    Toast.makeText(TeamAdminActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                Log.d("Response", "onFailure: " + t.toString());
            }
        });
        Log.d(TAG, "onClick: invite clicked");
    }

    //Toolbar back button onclick handling
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
