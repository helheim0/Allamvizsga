package com.example.esemenyszervezes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Result;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateTeamActivity extends AppCompatActivity {
    private EditText mName, mDescription;
    private Button mButton;
    private static String token;
    private static int id;
    private static final String TAG = "CreateTeamActivity";
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = CreateTeamActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        setupBottomNavigationView();

        mName = findViewById(R.id.team_name_et);
        mDescription = findViewById(R.id.desc_et);
        mButton = findViewById(R.id.create_team_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                String description = mDescription.getText().toString().trim();

                final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
                progressDialog.setMessage("Creating team...");
                progressDialog.show();

                Log.d(TAG, "onClick: called");
                ApiService service = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
                Call<Result> call = service.createTeam("Bearer "+token, name, description, id);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            token = response.headers().get("Authorization");
                            id = response.body().getUserId();
                            Intent home = new Intent(getApplicationContext(), TeamAdminActivity.class);
                            home.putExtra("token", response.body().getAccess_token());
                            startActivity(home);
                        } else {
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
}
