package com.example.esemenyszervezes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.BottomNavigationHelper;
import com.example.esemenyszervezes.pojo.Result;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateTeamActivity extends AppCompatActivity {
    private EditText mName, mDescription;
    private ImageView mImage;
    private Button mButton;
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
        mImage = findViewById(R.id.imgUpload);
        mButton = findViewById(R.id.create_team_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();

                final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
                progressDialog.setMessage("Logging in...");
                progressDialog.show();

                Log.d(TAG, "onClick: called");


                // RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), mImage);

                // MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestBody);
                ApiService service = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);


                Call<Result> call = service.createTeam(name);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        progressDialog.dismiss();
                        if (!response.body().getError()) {
                            Intent home = new Intent(getApplicationContext(), HomePageActivity.class);
                            home.putExtra("token", response.body().getAccess_token());
                            startActivity(home);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_LONG).show();
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

    private void uploadImage(String path) {
        Retrofit retrofit = RetrofitBuilder.getRetrofitInstance();

        ApiService service = retrofit.create(ApiService.class);

        File file = new File(path);

        // RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), mImage);

        // MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestBody);

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
