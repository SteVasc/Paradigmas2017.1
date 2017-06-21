package com.example.stefany.paradigmas20171.infrastructure;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.access.LoginActivity;
import com.example.stefany.paradigmas20171.access.ProfileActivity;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        //TODO implement initialization processes

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO check if user is logged in local database to update Session class
                if (Session.isLogged()){
                    Intent intentProfile = new Intent(InitialActivity.this, ProfileActivity.class);
                    finish();
                    startActivity(intentProfile);
                } else {
                    Intent intentLogin = new Intent(InitialActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intentLogin);
                }
            }
        }, 4000);
    }

}
