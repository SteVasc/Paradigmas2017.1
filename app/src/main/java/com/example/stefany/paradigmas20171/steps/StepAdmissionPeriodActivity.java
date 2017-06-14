package com.example.stefany.paradigmas20171.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.access.LoginActivity;
import com.example.stefany.paradigmas20171.access.ProfileActivity;
import com.example.stefany.paradigmas20171.infrastructure.Session;

public class StepAdmissionPeriodActivity extends AppCompatActivity {

    EditText editTextAdmissionYear;
    Button buttonContinue;
    Button buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_period);
        editTextAdmissionYear = (EditText) findViewById(R.id.edit_text_year);
        buttonContinue = (Button) findViewById(R.id.button_affirmative);
        buttonExit = (Button) findViewById(R.id.button_negative);

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int admissionYear = Integer.parseInt(editTextAdmissionYear.getText().toString());
                //TODO implement logic to compare entries, actually hardcoded
                Session.setPeriods(admissionYear, true);
                Intent intentContinue = new Intent(StepAdmissionPeriodActivity.this, StepLockingAskActivity.class);
                startActivity(intentContinue);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.isLogged()){
                    Intent intentBackProfile = new Intent(StepAdmissionPeriodActivity.this, ProfileActivity.class);
                    startActivity(intentBackProfile);
                    finish();
                } else {
                    Intent intentBackLogin = new Intent(StepAdmissionPeriodActivity.this, LoginActivity.class);
                    startActivity(intentBackLogin);
                    finish();
                }
            }
        });
    }
    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_first_entry:
                if (checked)
                    //first entry of the year
                    break;
            case R.id.radio_second_entry:
                if (checked)
                    //second entry of the year
                    break;
        }
    }
}
