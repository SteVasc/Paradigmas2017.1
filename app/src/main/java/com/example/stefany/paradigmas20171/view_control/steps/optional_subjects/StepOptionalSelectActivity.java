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

import java.util.ArrayList;

public class StepOptionalSelectActivity extends AppCompatActivity {

    private ArrayList<Subject> subjects = new ArrayList<>();
    private ArrayList<Subject> allSubjects = new ArrayList<>();
    private ListView listView;
    private SearchView searchView;
    private Button btnContinue;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_optional_select);
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
                int nextCode = subject.getInclusionStatus().getCode() + 1;
                int codeLooped = ((nextCode) % 4);
                changeListItemColor(view, subject, codeLooped);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStepState();
                Intent intentRepeat = new Intent(StepOptionalSelectActivity.this, StepExcludedOptionalAskActivity.class);
                finish();
                startActivity(intentRepeat);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBackProfile = new Intent(StepOptionalSelectActivity.this, ProfileActivity.class);
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
    public void updateStepState(){
        for (Subject subject : subjects){
            if (subject.getStatus() != SubjectStatus.NOT_ATTENDED){
                Session.getSubjectManager().updateSubject(subject);
            }
        }
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
    public void getAllSubjects(){
        allSubjects.addAll(Session.getSubjectManager().getPeriod(0).getSubjects());
    }
    public void populate(){
        ArrayAdapter<Subject> adapter = new StepOptionalSelectActivity.SubjectListAdapter();
        listView.setAdapter(adapter);
    }

    private class SubjectListAdapter extends ArrayAdapter<Subject> {


        public SubjectListAdapter(){
            super(StepOptionalSelectActivity.this, R.layout.list_item_subjects_status, subjects);
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
            SubjectStatus status = subject.getStatus();
            TextView subjectStatus = (TextView) view.findViewById(R.id.text_status);
            subjectStatus.setText("Status: " + status.getDescription());
            changeListItemColor(view, subject,status.getCode());
            return view;
        }
    }
}
