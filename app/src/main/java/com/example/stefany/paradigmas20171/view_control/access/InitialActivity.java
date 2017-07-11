package com.example.stefany.paradigmas20171.view_control.access;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.model.infrastructure.SubjectManager;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        Session.setContext(this);
        Session.setServerAddress("http://192.168.40.101:5000/");
        Session.setSubjectManager(new SubjectManager());

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
