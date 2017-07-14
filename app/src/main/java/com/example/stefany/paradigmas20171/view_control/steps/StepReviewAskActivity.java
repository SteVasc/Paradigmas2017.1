package com.example.stefany.paradigmas20171.view_control.steps;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.view_control.steps.optional_subjects.StepOptionalSelectActivity;
import com.example.stefany.paradigmas20171.view_control.steps.required_subjects.StepSemesterSubjectsActivity;

public class StepReviewAskActivity extends AppCompatActivity{

    private Button btnRequiredReview;
    private Button btnOptionalReview;
    private Button btnBackToBegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_review_ask);

        btnBackToBegin = (Button) findViewById(R.id.button_back_to_begin);
        btnOptionalReview = (Button) findViewById(R.id.button_optional_review);
        btnRequiredReview = (Button) findViewById(R.id.button_required_review);

        btnRequiredReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBackProfile = new Intent(StepReviewAskActivity.this, StepSemesterSubjectsActivity.class);
                StepSemesterSubjectsActivity.setReview(true);
                startActivity(intentBackProfile);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        btnBackToBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBackProfile = new Intent(StepReviewAskActivity.this, StepSemesterSubjectsActivity.class);
                StepSemesterSubjectsActivity.setReview(false);
                startActivity(intentBackProfile);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        btnOptionalReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBackProfile = new Intent(StepReviewAskActivity.this, StepOptionalSelectActivity.class);
                startActivity(intentBackProfile);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intentForward = new Intent(StepReviewAskActivity.this, StepFinalizeAskActivity.class);
        finish();
        startActivity(intentForward);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
