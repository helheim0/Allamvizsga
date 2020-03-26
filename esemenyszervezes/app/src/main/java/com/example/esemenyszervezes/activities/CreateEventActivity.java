package com.example.esemenyszervezes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.esemenyszervezes.R;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mName, mDate, mLocation, mDescription;
    private ImageView mImage;
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        mName = findViewById(R.id.eventName_et);
        mDescription = findViewById(R.id.desc_et);
        mLocation = findViewById(R.id.location_et);
        mDate = findViewById(R.id.date_et);
        mImage = findViewById(R.id.eventImgUpload);
        mButton = findViewById(R.id.create_button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = mName.getText().toString().trim();
        String description = mDescription.getText().toString().trim();
        String date = mDate.getText().toString().trim();
        String location = mLocation.getText().toString().trim();

    }
}
