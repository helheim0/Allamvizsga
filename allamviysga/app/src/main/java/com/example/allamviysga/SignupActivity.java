package com.example.allamviysga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private EditText mEmail, mPassword, mPasswordRep, mName, mUsername, mPhone;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mEmail = (EditText) findViewById(R.id.email_edittext);
        mPassword = (EditText) findViewById(R.id.password_edittext);
        mPasswordRep = (EditText) findViewById(R.id.reppsw_edittext);
        mName = (EditText) findViewById(R.id.name_edittext);
        mUsername = (EditText) findViewById(R.id.username_edittext);
        mPhone = (EditText) findViewById(R.id.phonenr_edittext);

        //Initializing Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        final Button signUp = (Button) findViewById(R.id.signup_button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String name = mName.getText().toString().trim();
                final String username = mUsername.getText().toString().trim();
                final String phone = mPhone.getText().toString().trim();
                final String passwordRep = mPasswordRep.getText().toString().trim();
                if(email.isEmpty()){
                    mEmail.setError("Email is required!");
                    mEmail.requestFocus();
                }
                else if(password.isEmpty()){
                    mPassword.setError("Password is required!");
                    mPassword.requestFocus();
                }
                else if(password.length()<6){
                    mPassword.setError("Password must have at least 6 characters.");
                    mPassword.requestFocus();
                }
                else if(!password.equals(passwordRep)){
                    mPasswordRep.setError("Passwords do not match!");
                    mPasswordRep.requestFocus();
                }
               else if (!email.isEmpty() && !password.isEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w("status", "createUserWithEmail: error", task.getException());
                                Toast.makeText(SignupActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("status", "createUserWithEmail: success");
                                firebaseUser = firebaseAuth.getCurrentUser();
                                Toast.makeText(SignupActivity.this, "Registration succeeded.", Toast.LENGTH_SHORT).show();
                                //   User user = new User(fullName, userName,useremail, phone);
                                //define this later   updateUI(user);
                                 FirebaseDatabase.getInstance().getReference();
                            }
                        }
                    });

                }
            }
        });
    }

    private void checkValidity(){

    }
    //functions
}
