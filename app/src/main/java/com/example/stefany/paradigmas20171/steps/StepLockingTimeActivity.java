package com.example.stefany.paradigmas20171.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.access.LoginActivity;
import com.example.stefany.paradigmas20171.access.ProfileActivity;
import com.example.stefany.paradigmas20171.infrastructure.Session;
import com.example.stefany.paradigmas20171.steps.required_subjects.StepPeriodSubjectsActivity;

public class StepLockingTimeActivity extends AppCompatActivity {

    private Button btnContinue;
    private Button btnExit;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_locking_time);
        btnContinue = (Button) findViewById(R.id.button_affirmative);
        btnExit = (Button) findViewById(R.id.button_negative);
        spinner = (Spinner) findViewById(R.id.spinner);

        String[] spinnerValues = new String[]{"1", "2", "3", "4"};
        spinner.setAdapter(new ArrayAdapter(StepLockingTimeActivity.this, R.layout.support_simple_spinner_dropdown_item, spinnerValues));

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGoLockingTime = new Intent(StepLockingTimeActivity.this, StepPeriodSubjectsActivity.class);
                startActivity(intentGoLockingTime);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.isLogged()){
                    Intent intentGoSubjects = new Intent(StepLockingTimeActivity.this, LoginActivity.class);
                    startActivity(intentGoSubjects);
                    finish();
                } else {
                    Intent intentGoSubjects = new Intent(StepLockingTimeActivity.this, ProfileActivity.class);
                    startActivity(intentGoSubjects);
                    finish();
                }
            }
        });
    }
}
