package com.example.stefany.paradigmas20171.steps.required_subjects;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.access.LoginActivity;
import com.example.stefany.paradigmas20171.access.ProfileActivity;
import com.example.stefany.paradigmas20171.infrastructure.Session;
import com.example.stefany.paradigmas20171.infrastructure.Subject;
import com.example.stefany.paradigmas20171.infrastructure.SubjectStatus;
import com.example.stefany.paradigmas20171.steps.StepAdmissionPeriodActivity;

import java.util.ArrayList;

public class StepPeriodSubjectsActivity extends AppCompatActivity {

    private static ArrayList<Subject> subjects;
    private static int numberOfScreens;
    private ListView listView;
    private Button btnContinue;
    private Button btnExit;
    private TextView textPeriodNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_period_subjects);
        listView = (ListView) findViewById(R.id.list_view);
        btnContinue = (Button) findViewById(R.id.button_affirmative);
        btnExit = (Button) findViewById(R.id.button_negative);
        textPeriodNumber = (TextView) findViewById(R.id.text_period_number);
        testSubjects();
        populateList();
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO implement repetition of screens
                Toast.makeText(StepPeriodSubjectsActivity.this, "To Implement", Toast.LENGTH_SHORT).show();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.isLogged()){
                    Intent intentBackProfile = new Intent(StepPeriodSubjectsActivity.this, ProfileActivity.class);
                    startActivity(intentBackProfile);
                    finish();

                } else {
                    Intent intentBackLogin = new Intent(StepPeriodSubjectsActivity.this, LoginActivity.class);
                    startActivity(intentBackLogin);
                    finish();
                }
            }
        });
    }

    public void populateList(){
        ArrayAdapter<Subject> adapter = new SubjectListAdapter();
        listView.setAdapter(adapter);
    }

    public static void setSubjects(ArrayList<Subject> subjects) {
        StepPeriodSubjectsActivity.subjects = subjects;
    }

    private class SubjectListAdapter extends ArrayAdapter<Subject>{


        public SubjectListAdapter(){
            super(StepPeriodSubjectsActivity.this, R.layout.list_item_subjects_status, subjects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.list_item_subjects_status, parent, false);
            }
            Subject subject = subjects.get(position);
            final String[] status = new String[]{SubjectStatus.NOT_ATTENDED.getDescription(),
                                            SubjectStatus.APPROVED.getDescription(),
                                            SubjectStatus.DISAPPROVED.getDescription(),
                                            SubjectStatus.STUDYING.getDescription()};
            TextView subjectName = (TextView) view.findViewById(R.id.text_subject_name);
            subjectName.setText(subject.getDescription());
            Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
            spinner.setAdapter(new ArrayAdapter(StepPeriodSubjectsActivity.this, R.layout.support_simple_spinner_dropdown_item, status));
            return view;
        }
    }

    private void testSubjects(){
        Subject subject = new Subject();
        subject.setDescription("Introdução à programação");
        Subject subject1 = new Subject();
        subject.setDescription("Matemática Discreta");
        Subject subject2 = new Subject();
        subject.setDescription("Cálculo a uma variaável");
        Subject subject3 = new Subject();
        subject.setDescription("Teoria geral da administração");
        Subject subject4 = new Subject();
        subject.setDescription("Laboratório de Informática");
        subjects = new ArrayList<>();
        subjects.add(subject);
        subjects.add(subject1);
        subjects.add(subject2);
        subjects.add(subject3);
        subjects.add(subject4);
    }

    public static void setNumberOfScreens(int numberOfScreens) {
        StepPeriodSubjectsActivity.numberOfScreens = numberOfScreens;
    }
}
