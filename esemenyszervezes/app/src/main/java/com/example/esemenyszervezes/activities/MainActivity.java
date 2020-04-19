package com.example.esemenyszervezes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.esemenyszervezes.R;

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
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if(settings.getString("logged", "").equals("logged")) {
            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(intent);
        }
    }

    public void onClickSignup(View v){
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    public void onClickLogin(View v){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
