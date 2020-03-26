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

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignUpActivity";
    private EditText mEmail, mPassword, mPasswordRep, mName, mUsername, mPhone;
    private Button mRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Getting data from recourse
        mEmail =  findViewById(R.id.email_et);
        mPassword = findViewById(R.id.psw_et);
        mPasswordRep =  findViewById(R.id.psw_rep_et);
        mName =  findViewById(R.id.fullName_et);
        mUsername = findViewById(R.id.username_et);
        mPhone =  findViewById(R.id.phone_et);
        mRegister = (Button) findViewById(R.id.signup_button);
        mRegister.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {

        String name = mUsername.getText().toString().trim();
        String fullName = mName.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String repPassword = mPasswordRep.getText().toString().trim();
        String phone = mPhone.getText().toString().trim();

        checkValidity(name, fullName, email, password, repPassword, phone);

        Log.d(TAG, "onClick: login button clicked");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        ApiService service = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
        Call<Result>  call = service.createUser(name, fullName, email, phone, password);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.w(TAG, "onResponse: "  +response );
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void checkValidity(String name, String fullName, String email, String password, String repPassword, String phone){

        if(name.length() < 5){
            mUsername.setError("Username must have at least 5 characters!");
            mUsername.requestFocus();
        }

        else if(name.isEmpty()){
            mUsername.setError("Username cannot be empty!");
            mUsername.requestFocus();
        }

        else if(password.isEmpty()){
            mPassword.setError("Password cannot be empty!");
            mPassword.requestFocus();
        }

        else if(password.length()<6){
            mPassword.setError("Password must contain at least 6 characters!");
            mPassword.requestFocus();
        }

        else if(fullName.isEmpty()){
            mName.setError("Name cannot be empty!");
            mName.requestFocus();
        }

        else if(email.isEmpty()){
            mEmail.setError("Email cannot be empty!");
            mEmail.requestFocus();
        }

        else if(phone.isEmpty()){
            mPhone.setError("Phone cannot be empty!");
            mPhone.requestFocus();
        }

        else if(repPassword.isEmpty()){
            mPasswordRep.setError("Password check cannot be empty!");
            mPasswordRep.requestFocus();
        }

        else if(!(password.equals(repPassword))){
            mPasswordRep.setError("Passwords do not match!");
            mPasswordRep.requestFocus();
        }
    }
}
