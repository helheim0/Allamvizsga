package com.example.allamviysga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginButton, signupButton,h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button)findViewById(R.id.login_btn);
        loginButton.setOnClickListener(this);
        signupButton = (Button)findViewById(R.id.signup_btn);
        signupButton.setOnClickListener(this);
        h = (Button)findViewById(R.id.button);
        h.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_btn:
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
                break;
            case R.id.login_btn:
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;

            case R.id.button:
                Intent in = new Intent(this, HomePage.class);
                startActivity(in);
                break;
            default:
                break;

        }

    }

}
