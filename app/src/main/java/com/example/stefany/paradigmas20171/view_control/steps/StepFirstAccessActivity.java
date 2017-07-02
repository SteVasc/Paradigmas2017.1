package com.example.stefany.paradigmas20171.view_control.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.view_control.access.LoginActivity;
import com.example.stefany.paradigmas20171.view_control.access.ProfileActivity;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;

public class StepFirstAccessActivity extends AppCompatActivity {
    Button buttonContinue;
    Button buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_steps);

        buttonContinue = (Button) findViewById(R.id.button_affirmative);
        buttonBack = (Button) findViewById(R.id.button_negative);

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGoAdmissionTime = new Intent(StepFirstAccessActivity.this, StepAdmissionPeriodActivity.class);
                startActivity(intentGoAdmissionTime);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.isLogged()){
                    Intent intentBackProfile = new Intent(StepFirstAccessActivity.this, ProfileActivity.class);
                    startActivity(intentBackProfile);
                    finish();
                } else {
                    Intent intentBackLogin = new Intent(StepFirstAccessActivity.this, LoginActivity.class);
                    startActivity(intentBackLogin);
                    finish();
                }
            }
        });
    }
}
