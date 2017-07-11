package com.example.stefany.paradigmas20171.view_control.steps.optional_subjects;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.view_control.steps.required_subjects.StepRequiredComplementAskActivity;
import com.example.stefany.paradigmas20171.view_control.steps.required_subjects.StepRequiredComplementSelectActivity;

public class StepOptionalStartActivity extends AppCompatActivity {

    private Button btnYes;
    private Button btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_optional_start);
        btnYes = (Button) findViewById(R.id.button_affirmative);
        btnNo = (Button) findViewById(R.id.button_negative);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGoLockingTime = new Intent(StepOptionalStartActivity.this, StepOptionalSelectActivity.class);
                startActivity(intentGoLockingTime);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGoSubjects = new Intent(StepOptionalStartActivity.this, StepExcludedOptionalAskActivity.class);
                startActivity(intentGoSubjects);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

    }
}
