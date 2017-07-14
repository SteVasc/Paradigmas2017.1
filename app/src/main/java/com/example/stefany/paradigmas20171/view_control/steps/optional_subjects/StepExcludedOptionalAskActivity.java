package com.example.stefany.paradigmas20171.view_control.steps.optional_subjects;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.view_control.steps.StepAdmissionSemesterActivity;
import com.example.stefany.paradigmas20171.view_control.steps.StepFinalizeAskActivity;
import com.example.stefany.paradigmas20171.view_control.steps.StepReviewAskActivity;
import com.example.stefany.paradigmas20171.view_control.steps.required_subjects.StepRequiredComplementAskActivity;

public class StepExcludedOptionalAskActivity extends AppCompatActivity {

    private Button btnYes;
    private Button btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_excluded_optional_ask);
        btnYes = (Button) findViewById(R.id.button_affirmative);
        btnNo = (Button) findViewById(R.id.button_negative);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGoLockingTime = new Intent(StepExcludedOptionalAskActivity.this, StepExcludedOptionalSelectActivity.class);
                startActivity(intentGoLockingTime);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGoSubjects = new Intent(StepExcludedOptionalAskActivity.this, StepFinalizeAskActivity.class);
                startActivity(intentGoSubjects);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intentForward = new Intent(StepExcludedOptionalAskActivity.this, StepOptionalStartActivity.class);
        finish();
        startActivity(intentForward);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
