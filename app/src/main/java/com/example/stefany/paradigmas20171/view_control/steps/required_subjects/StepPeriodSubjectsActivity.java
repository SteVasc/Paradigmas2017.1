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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.view_control.access.LoginActivity;
import com.example.stefany.paradigmas20171.view_control.access.ProfileActivity;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.model.infrastructure.Subject;
import com.example.stefany.paradigmas20171.model.infrastructure.SubjectStatus;

import java.util.ArrayList;

public class StepPeriodSubjectsActivity extends AppCompatActivity {

    private static ArrayList<Subject> subjects;
    private static int numberOfScreens;
    private static Integer periodNumber;
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
        textPeriodNumber.setText("Periodo: " + periodNumber.toString());
        populateList();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hardcoded Test
                if (periodNumber < numberOfScreens){
                    StepPeriodSubjectsActivity.setSubjects(testScreenPassing());
                    StepPeriodSubjectsActivity.setPeriodNumber(periodNumber+1);
                    Intent intentRepeat = new Intent(StepPeriodSubjectsActivity.this, StepPeriodSubjectsActivity.class);
                    finish();
                    startActivity(intentRepeat);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                } else {
                    Intent intentForward = new Intent(StepPeriodSubjectsActivity.this, StepRequiredComplementAskActivity.class);
                    finish();
                    startActivity(intentForward);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                }
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
    public void changeListItemColor(int position, int colorNumber){
        switch (colorNumber){
            case 0:
                //Gray color
                this.listView.getChildAt(position).setBackgroundColor(Color.parseColor("#6f7279"));
                break;
            case 1:
                //Green color
                this.listView.getChildAt(position).setBackgroundColor(Color.parseColor("#66cc33"));
                break;
            case 2:
                //Red color
                this.listView.getChildAt(position).setBackgroundColor(Color.parseColor("#df5a3e"));
                break;
            case 3:
                //Yellow color
                this.listView.getChildAt(position).setBackgroundColor(Color.parseColor("#ffcc00"));
                break;
        }
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
        public View getView(final int position, @Nullable View view, @NonNull ViewGroup parent) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.list_item_subjects_status, parent, false);
            }
            final Subject subject = subjects.get(position);
            final SubjectStatus[] status = new SubjectStatus[]{SubjectStatus.NOT_ATTENDED,
                    SubjectStatus.APPROVED,
                    SubjectStatus.DISAPPROVED,
                    SubjectStatus.STUDYING};
            final String[] statusStrings = new String[]{SubjectStatus.NOT_ATTENDED.getDescription(),
                                            SubjectStatus.APPROVED.getDescription(),
                                            SubjectStatus.DISAPPROVED.getDescription(),
                                            SubjectStatus.STUDYING.getDescription()};
            TextView subjectName = (TextView) view.findViewById(R.id.text_subject_name);
            subjectName.setText(subject.getDescription());
            Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
            spinner.setAdapter(new ArrayAdapter(StepPeriodSubjectsActivity.this, R.layout.support_simple_spinner_dropdown_item, statusStrings));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int selected, long l) {
                    changeListItemColor(position, selected);
                    subject.setStatus(status[selected]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    changeListItemColor(position, 3);
                    subject.setStatus(status[3]);
                }
            });
            return view;
        }
    }

    public static void testSubjects(){
        ArrayList<Subject> subjects1 = new ArrayList();
        Subject subject = new Subject();
        subject.setDescription("Introdução à programação");
        Subject subject1 = new Subject();
        subject1.setDescription("Matemática Discreta");
        Subject subject2 = new Subject();
        subject2.setDescription("Cálculo a uma variável");
        Subject subject3 = new Subject();
        subject3.setDescription("Teoria geral da administração");
        Subject subject4 = new Subject();
        subject4.setDescription("Laboratório de Informática");
        subjects1.add(subject);
        subjects1.add(subject1);
        subjects1.add(subject2);
        subjects1.add(subject3);
        subjects1.add(subject4);
        setSubjects(subjects1);
        setNumberOfScreens(2);
    }
    private ArrayList<Subject> testScreenPassing(){
        ArrayList<Subject> subjects1 = new ArrayList();
        Subject subject = new Subject();
        subject.setDescription("Introdução à Teoria da Computação");
        Subject subject1 = new Subject();
        subject1.setDescription("Algoritmo e estrutura de dados");
        Subject subject2 = new Subject();
        subject2.setDescription("Cálculo a várias variáveis");
        Subject subject3 = new Subject();
        subject3.setDescription("Fundamentos de Sistemas de Informação");
        Subject subject4 = new Subject();
        subject4.setDescription("Laboratório de Programação");
        subjects1.add(subject);
        subjects1.add(subject1);
        subjects1.add(subject2);
        subjects1.add(subject3);
        subjects1.add(subject4);
        return subjects1;
    }

    public static void setNumberOfScreens(int numberOfScreens) {
        StepPeriodSubjectsActivity.numberOfScreens = numberOfScreens;
    }

    public static void setPeriodNumber(Integer periodNumber) {
        StepPeriodSubjectsActivity.periodNumber = periodNumber;
    }
}
