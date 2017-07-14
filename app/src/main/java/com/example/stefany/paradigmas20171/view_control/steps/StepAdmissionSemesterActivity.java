package com.example.stefany.paradigmas20171.view_control.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.view_control.access.LoginActivity;
import com.example.stefany.paradigmas20171.view_control.access.ProfileActivity;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.view_control.steps.required_subjects.StepSemesterSubjectsActivity;

import java.util.Calendar;

public class StepAdmissionSemesterActivity extends AppCompatActivity {

    private EditText editTextAdmissionYear;
    private RadioGroup group;
    private Button buttonContinue;
    private Button buttonExit;
    private boolean lockIsPossible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_period);
        editTextAdmissionYear = (EditText) findViewById(R.id.edit_text_year);
        buttonContinue = (Button) findViewById(R.id.button_affirmative);
        buttonExit = (Button) findViewById(R.id.button_negative);
        group = (RadioGroup) findViewById(R.id.radio_group);




        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int admissionYear = Integer.parseInt(editTextAdmissionYear.getText().toString());
                    int actualYear = Calendar.getInstance().get(Calendar.YEAR);
                    if (admissionYear < (actualYear - 11)){
                        throw new Exception();
                    } else if (admissionYear > actualYear){
                        throw new Exception();
                    } else if (admissionYear == actualYear){
                        lockIsPossible = false;
                    }
                    int radioButtonID = group.getCheckedRadioButtonId();
                    View radioButton = group.findViewById(radioButtonID);
                    int index = group.indexOfChild(radioButton);
                    Session.setSemesters(admissionYear, index + 1);
                    changeScreen();
                } catch (Exception e){
                    e.printStackTrace();
                    editTextAdmissionYear.setError("Entrada Inválida");
                }
            }
        });
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.isLogged()){
                    Intent intentBackProfile = new Intent(StepAdmissionSemesterActivity.this, ProfileActivity.class);
                    startActivity(intentBackProfile);
                    finish();
                } else {
                    Intent intentBackLogin = new Intent(StepAdmissionSemesterActivity.this, LoginActivity.class);
                    startActivity(intentBackLogin);
                    finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (Session.isLogged()){
            Intent intentBackProfile = new Intent(StepAdmissionSemesterActivity.this, ProfileActivity.class);
            startActivity(intentBackProfile);
            finish();
        } else {
            Intent intentBackLogin = new Intent(StepAdmissionSemesterActivity.this, LoginActivity.class);
            startActivity(intentBackLogin);
            finish();
        }
    }

    private void changeScreen() {
        if (lockIsPossible) {
            Intent intentContinue = new Intent(StepAdmissionSemesterActivity.this, StepLockingAskActivity.class);
            startActivity(intentContinue);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        } else {
            StepSemesterSubjectsActivity.setSemesterNumber(1);
            Intent intentContinue = new Intent(StepAdmissionSemesterActivity.this, StepSemesterSubjectsActivity.class);
            startActivity(intentContinue);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
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
