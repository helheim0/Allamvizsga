package com.example.esemenyszervezes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LoginActivity";
    public static final String PREFS_NAME = "LoginPrefs";
    private static String token;
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

        if(mUsername.isEmpty()){
            username.setError("Email is required!");
            username.requestFocus();
        }
        else if(mPassword.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
        }
        else{
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        ApiService service = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
        Call<Result> call = service.loginUser(mUsername, mPassword);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if(true){
                        editor.putString("email", "");
                        editor.putString("password", "");
                    }
                    editor.apply();

                    Intent home = new Intent(getApplicationContext(), HomePageActivity.class);
                    Toast.makeText(getApplicationContext(), "Successful Login", Toast.LENGTH_SHORT).show();
                    assert response.body() != null;
                    token = response.body().getAccess_token();
                    home.putExtra("email", mUsername);
                    home.putExtra("password", mPassword);
                    startActivity(home);
                    finish();
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
    }

    private void keepLoggedIn(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = preferences.edit();

        //Saving data
        editor.putBoolean("key_name", true);
        editor.putString("key_name", "string_value");

        //Retrieving data
        preferences.getBoolean("key_name", false);
        preferences.getString("key_name", null);

        //Deleting data
        editor.remove("email");
        editor.remove("password");

        editor.commit();

        editor.clear();
        editor.commit();
    }
    /*@Override
    protected void onDestroy(){
        super.onDestroy();
        if (call != null){
            call.cancel();
            call = null;
        }
    }*/

}
