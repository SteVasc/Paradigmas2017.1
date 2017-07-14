package com.example.stefany.paradigmas20171.view_control.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.view_control.steps.required_subjects.StepSemesterSubjectsActivity;

public class StepLockingAskActivity extends AppCompatActivity {

    Button btnYes;
    Button btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_locking_ask);
        btnYes = (Button) findViewById(R.id.button_affirmative);
        btnNo = (Button) findViewById(R.id.button_negative);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGoLockingTime = new Intent(StepLockingAskActivity.this, StepLockingTimeActivity.class);
                startActivity(intentGoLockingTime);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StepSemesterSubjectsActivity.setSemesterNumber(1);
                Intent intentGoSubjects = new Intent(StepLockingAskActivity.this, StepSemesterSubjectsActivity.class);
                startActivity(intentGoSubjects);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intentForward = new Intent(StepLockingAskActivity.this, StepAdmissionSemesterActivity.class);
        finish();
        startActivity(intentForward);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
