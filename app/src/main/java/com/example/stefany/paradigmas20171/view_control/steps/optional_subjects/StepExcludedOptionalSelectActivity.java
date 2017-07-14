package com.example.stefany.paradigmas20171.view_control.steps.optional_subjects;

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
import android.widget.SearchView;
import android.widget.TextView;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.model.infrastructure.Subject;
import com.example.stefany.paradigmas20171.model.infrastructure.SubjectStatus;
import com.example.stefany.paradigmas20171.view_control.access.ProfileActivity;
import com.example.stefany.paradigmas20171.view_control.steps.StepAdmissionSemesterActivity;
import com.example.stefany.paradigmas20171.view_control.steps.StepFinalizeAskActivity;
import com.example.stefany.paradigmas20171.view_control.steps.StepReviewAskActivity;
import com.example.stefany.paradigmas20171.view_control.steps.required_subjects.StepRequiredComplementAskActivity;

import java.util.ArrayList;

public class StepExcludedOptionalSelectActivity extends AppCompatActivity {

    private ArrayList<Subject> subjects = new ArrayList<>();
    private ArrayList<Subject> allSubjects = new ArrayList<>();
    private ListView listView;
    private SearchView searchView;
    private Button btnContinue;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_excluded_optional_select);
        listView = (ListView) findViewById(R.id.list_view);
        searchView = (SearchView) findViewById(R.id.search_subject);
        btnContinue = (Button) findViewById(R.id.button_affirmative);
        btnExit = (Button) findViewById(R.id.button_negative);
        getAllSubjects();
        subjects = allSubjects;
        populate();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Subject subject = subjects.get(i);
                int nextCode = subject.getInclusionStatus().getCode()+1;
                int codeLooped = ((nextCode)%2);
                int finalCode = codeLooped+4;
                changeListItemColor(view, subject, finalCode);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStepState();
                Intent intentRepeat = new Intent(StepExcludedOptionalSelectActivity.this, StepFinalizeAskActivity.class);
                finish();
                startActivity(intentRepeat);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBackProfile = new Intent(StepExcludedOptionalSelectActivity.this, ProfileActivity.class);
                startActivity(intentBackProfile);
                finish();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() > 0) {
                    ArrayList<Subject> listAux = new ArrayList<>();
                    for (Subject subject : subjects) {
                        if (subject.getDescription().toLowerCase().contains(s.toLowerCase())){
                            listAux.add(subject);
                        }
                    }
                    subjects= listAux;
                } else {
                    subjects = allSubjects;
                }
                populate();
                return false;
            }
        });
    }
    public void changeListItemColor(View view, Subject subject, int code){
        TextView status = (TextView) view.findViewById(R.id.text_status);
        switch (code){
            case 4:
                //Green color
                view.setBackgroundColor(Color.parseColor("#66cc33"));
                status.setText("Status: " + "Cursar");
                subject.setInclusionStatus(SubjectStatus.TO_ATTEND);
                break;
            case 5:
                //Red color
                view.setBackgroundColor(Color.parseColor("#df5a3e"));
                status.setText("Status: " + "Não cursar");
                subject.setInclusionStatus(SubjectStatus.TO_NOT_ATTEND);
                break;
        }
    }
    public void updateStepState(){
        for (Subject subject : subjects){
            if (subject.getInclusionStatus() != SubjectStatus.TO_ATTEND){
                Session.getSubjectManager().excludeSubject(subject);
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent intentForward = new Intent(StepExcludedOptionalSelectActivity.this, StepExcludedOptionalAskActivity.class);
        finish();
        startActivity(intentForward);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
    public void getAllSubjects(){
        allSubjects.addAll(Session.getSubjectManager().getPeriod(0).getSubjects());
    }
    public void populate(){
        ArrayAdapter<Subject> adapter = new StepExcludedOptionalSelectActivity.SubjectListAdapter();
        listView.setAdapter(adapter);
    }

    private class SubjectListAdapter extends ArrayAdapter<Subject> {


        public SubjectListAdapter(){
            super(StepExcludedOptionalSelectActivity.this, R.layout.list_item_subjects_status, subjects);
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
            SubjectStatus status = subject.getInclusionStatus();
            TextView subjectStatus = (TextView) view.findViewById(R.id.text_status);
            subjectStatus.setText("Status: " + status.getDescription());
            changeListItemColor(view, subject,status.getCode());
            return view;
        }
    }
}
