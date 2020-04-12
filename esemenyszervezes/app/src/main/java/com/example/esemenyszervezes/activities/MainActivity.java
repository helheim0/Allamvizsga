package com.example.esemenyszervezes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.pojo.SaveSharedPrefs;

public class MainActivity extends AppCompatActivity {
Button register, login;
public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = findViewById(R.id.main_signup);
        login = findViewById(R.id.main_login);

        //Checking if user is already logged in
        if(SaveSharedPrefs.getLoggedStatus(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
            startActivity(intent);
        }
    }

    public void onClickSignup(View v){
        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
        startActivity(intent);
    }

    public void onClickLogin(View v){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
