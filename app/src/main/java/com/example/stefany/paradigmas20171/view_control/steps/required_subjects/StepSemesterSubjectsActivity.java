package com.example.stefany.paradigmas20171.view_control.steps.required_subjects;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.model.infrastructure.Semester;
import com.example.stefany.paradigmas20171.view_control.access.LoginActivity;
import com.example.stefany.paradigmas20171.view_control.access.ProfileActivity;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.model.infrastructure.Subject;
import com.example.stefany.paradigmas20171.model.infrastructure.SubjectStatus;
import com.example.stefany.paradigmas20171.view_control.steps.StepAdmissionSemesterActivity;
import com.example.stefany.paradigmas20171.view_control.steps.StepFinalizeAskActivity;

import java.util.ArrayList;

public class StepSemesterSubjectsActivity extends AppCompatActivity {

    private static ArrayList<Subject> subjects;
    private static int numberOfScreens;
    private static Integer periodNumber;
    private ListView listView;
    private Button btnContinue;
    private Button btnExit;
    private TextView textPeriodNumber;
    private static boolean review = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_period_subjects);
        listView = (ListView) findViewById(R.id.list_view);
        btnContinue = (Button) findViewById(R.id.button_affirmative);
        btnExit = (Button) findViewById(R.id.button_negative);
        textPeriodNumber = (TextView) findViewById(R.id.text_period_number);
        textPeriodNumber.setText("Periodo: " + periodNumber.toString());
        numberOfScreens = Session.getSemesters();
        getSubjectsByPeriod();
        populateList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Subject subject = subjects.get(i);
                changeListItemColor(view, subject,(subject.getStatus().getCode()+1)%4);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStepState();
                if (periodNumber <= numberOfScreens){
                    StepSemesterSubjectsActivity.setSemesterNumber(periodNumber+1);
                    Intent intentRepeat = new Intent(StepSemesterSubjectsActivity.this, StepSemesterSubjectsActivity.class);
                    finish();
                    startActivity(intentRepeat);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                } else {
                    if (!review) {
                        Intent intentForward = new Intent(StepSemesterSubjectsActivity.this, StepRequiredComplementAskActivity.class);
                        finish();
                        startActivity(intentForward);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    } else {
                        Intent intentForward = new Intent(StepSemesterSubjectsActivity.this, StepFinalizeAskActivity.class);
                        finish();
                        startActivity(intentForward);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }
                }
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.isLogged()){
                    Intent intentBackProfile = new Intent(StepSemesterSubjectsActivity.this, ProfileActivity.class);
                    startActivity(intentBackProfile);
                    finish();

                } else {
                    Intent intentBackLogin = new Intent(StepSemesterSubjectsActivity.this, LoginActivity.class);
                    startActivity(intentBackLogin);
                    finish();
                }
            }
        });
    }

    public void updateStepState(){
        for (Subject subject : subjects){
            if (subject.getStatus() != SubjectStatus.NOT_ATTENDED){
                Session.getSubjectManager().updateSubject(subject);
            }
        }
    }

    public static void setReview(boolean review) {
        StepSemesterSubjectsActivity.review = review;
    }

    public void populateList(){
        ArrayAdapter<Subject> adapter = new SubjectListAdapter();
        listView.setAdapter(adapter);
    }
    public void changeListItemColor(View view, Subject subject, int code){
        TextView status = (TextView) view.findViewById(R.id.text_status);
        switch (code){
            case 0:
                //Gray color
                view.setBackgroundColor(Color.parseColor("#6f7279"));
                status.setText("Status: " + "Não cursado");
                subject.setStatus(SubjectStatus.NOT_ATTENDED);
                break;
            case 1:
                //Green color
                view.setBackgroundColor(Color.parseColor("#66cc33"));
                status.setText("Status: " + "Aprovado");
                subject.setStatus(SubjectStatus.APPROVED);
                break;
            case 2:
                //Red color
                view.setBackgroundColor(Color.parseColor("#df5a3e"));
                status.setText("Status: " + "Reprovado");
                subject.setStatus(SubjectStatus.DISAPPROVED);
                break;
            case 3:
                //Yellow color
                view.setBackgroundColor(Color.parseColor("#ffcc00"));
                status.setText("Status: " + "Cursando");
                subject.setStatus(SubjectStatus.STUDYING);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intentForward = new Intent(StepSemesterSubjectsActivity.this, StepAdmissionSemesterActivity.class);
        finish();
        startActivity(intentForward);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void getSubjectsByPeriod(){
        Semester semester = Session.getSubjectManager().getPeriod(periodNumber);
        setSubjects(semester.getSubjects());
    }

    public static void setSubjects(ArrayList<Subject> subjects) {
        StepSemesterSubjectsActivity.subjects = subjects;
    }

    private class SubjectListAdapter extends ArrayAdapter<Subject>{


        public SubjectListAdapter(){
            super(StepSemesterSubjectsActivity.this, R.layout.list_item_subjects_status, subjects);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View view, @NonNull ViewGroup parent) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.list_item_subjects_status, parent, false);
            }
            final Subject subject = subjects.get(position);
            final SubjectStatus[] statusArray = new SubjectStatus[]{SubjectStatus.NOT_ATTENDED,
                    SubjectStatus.APPROVED,
                    SubjectStatus.DISAPPROVED,
                    SubjectStatus.STUDYING};
            final String[] statusStrings = new String[]{SubjectStatus.NOT_ATTENDED.getDescription(),
                                            SubjectStatus.APPROVED.getDescription(),
                                            SubjectStatus.DISAPPROVED.getDescription(),
                                            SubjectStatus.STUDYING.getDescription()};
            TextView subjectName = (TextView) view.findViewById(R.id.text_subject_name);
            subjectName.setText(subject.getDescription());
            TextView subjectStatus = (TextView) view.findViewById(R.id.text_status);
            SubjectStatus status = subject.getStatus();
            subjectStatus.setText("Status: " + status.getDescription());
            changeListItemColor(view, subject,status.getCode());
            return view;
        }
    }
    public static void setSemesterNumber(Integer periodNumber) {
        StepSemesterSubjectsActivity.periodNumber = periodNumber;
    }
}
