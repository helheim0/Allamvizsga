package com.example.esemenyszervezes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        String mUsername = username.getText().toString().trim();
        String mPassword = password.getText().toString().trim();

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
