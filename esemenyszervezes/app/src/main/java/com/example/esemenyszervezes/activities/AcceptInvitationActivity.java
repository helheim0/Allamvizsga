package com.example.esemenyszervezes.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.esemenyszervezes.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AcceptInvitationActivity extends AppCompatActivity implements View.OnClickListener{
private ImageView mImage;
private TextView mDescription, mDecline;
private Button mAccept;
private EditText mCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_invitation);

        mImage = findViewById(R.id.eventName_et);
        mDescription = findViewById(R.id.desc_et);
        mCode = findViewById(R.id.invitationCodeEt);
        mAccept = findViewById(R.id.acceptBtn);
        mAccept.setOnClickListener(this);

        mDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcceptInvitationActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        String code = mCode.getText().toString().trim();

    }

}
