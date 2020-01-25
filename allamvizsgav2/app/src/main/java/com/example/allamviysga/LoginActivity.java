package com.example.allamviysga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginemail_edittext);
        password = findViewById(R.id.loginpsw_edittext);
        login = findViewById(R.id.loginactivity_btn);

        //Initializing Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mEmail = email.getText().toString().trim();
                final String mPassword = password.getText().toString().trim();


                if(mEmail.isEmpty()){
                    email.setError("Email is required!");
                    email.requestFocus();
                }
                else if(mPassword.isEmpty()){
                    password.setError("Password is required!");
                    password.requestFocus();
                }
                else if(!mEmail.isEmpty() && !mPassword.isEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Log.w("status", "signInWithEmail:failure: ", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                            }
                            else{

                                Log.d("status", "signInWithEmail:success");
                                firebaseUser = firebaseAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "Authentication succeeded", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, HomePage.class);
                                startActivity(intent);
                            }
                        }
                    });

                }

            }
        });
    }
    @Override
    public void onStart(){
        super.onStart();
        firebaseUser = firebaseAuth.getCurrentUser();
    }
}
