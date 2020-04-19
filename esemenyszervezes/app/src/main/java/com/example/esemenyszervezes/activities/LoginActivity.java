package com.example.esemenyszervezes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.pojo.Result;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.SaveSharedPrefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LoginActivity";
    public static final String PREFS_NAME = "LoginPrefs";
    private Context mContext;
    private static String token;
    private static int id;
    private EditText username;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.loginuser_edittext);
        password = findViewById(R.id.loginpsw_edittext);
        login = findViewById(R.id.loginactivity_btn);
        login.setOnClickListener(this);
        Log.d(TAG, "onCreate: called");
    }

    @Override
    public void onClick(View v) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        CheckBox checkBox = findViewById(R.id.rmbr_checkBox);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putString("logged", "logged");

        final String mUsername = username.getText().toString().trim();
        final String mPassword = password.getText().toString().trim();

        if (mUsername.isEmpty()) {
            username.setError("Email is required!");
            username.requestFocus();
        } else if (mPassword.isEmpty()) {
            password.setError("Password is required!");
            password.requestFocus();
        } else {
            progressDialog.setMessage("Logging in...");
            progressDialog.show();

            ApiService service = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
            Call<Result> call = service.loginUser(mUsername, mPassword);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        Log.d(TAG, "onResponse: " + response.body());
                        assert response.body() != null;
                        token = response.body().getAccess_token();
                        id = response.body().getUserId();

                        editor.putString("token", token);
                        editor.putInt("id", id);
                        editor.putString("logged", "logged");
                        editor.apply();
                        Log.d(TAG, "onResponse: token + id passed" + token + " " + id);
                     //   SaveSharedPrefs.setLoggedIn(getApplicationContext(), true);
                        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Credentials are not Valid.",
                                Toast.LENGTH_SHORT).show();
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
    /*@Override
    protected void onDestroy(){
        super.onDestroy();
        if (call != null){
            call.cancel();
            call = null;
        }
    }*/

    }}
