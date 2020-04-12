package com.example.esemenyszervezes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.Event;
import com.example.esemenyszervezes.pojo.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "CreateEventActivity";
    private static String token;
    private EditText mName, mDate, mLocation, mDescription;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        mName = findViewById(R.id.eventName_et);
        mDescription = findViewById(R.id.desc_et);
        mLocation = findViewById(R.id.location_et);
        mDate = findViewById(R.id.date_et);
        mButton = findViewById(R.id.create_button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = mName.getText().toString().trim();
        String description = mDescription.getText().toString().trim();
        String date = mDate.getText().toString().trim();
        String location = mLocation.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Creating event...");
        progressDialog.show();
        Log.d(TAG, "onClick: called");

        ApiService service = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);

         Call<List<Event>> call = service.createEvent("Bearer "+token, name, description, date, location);
                call.enqueue(new Callback<List<Event>>() {
                    @Override
                    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            Log.d("Error", "Event created");
                            Toast.makeText(getApplicationContext(), "Event created successfully!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Event>> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("Error", t.getMessage());
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

}
