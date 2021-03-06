package com.example.stefany.paradigmas20171.view_control.steps;

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
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.model.infrastructure.Subject;
import com.example.stefany.paradigmas20171.model.infrastructure.SubjectStatus;
import com.example.stefany.paradigmas20171.view_control.results.WaitingForResultsActivity;
import com.example.stefany.paradigmas20171.view_control.steps.optional_subjects.StepExcludedOptionalAskActivity;
import com.example.stefany.paradigmas20171.view_control.steps.optional_subjects.StepOptionalStartActivity;
import com.example.stefany.paradigmas20171.view_control.steps.required_subjects.StepRequiredComplementAskActivity;

import java.util.ArrayList;

public class StepFinalizeAskActivity extends AppCompatActivity {
    private ArrayList<Subject> subjects = new ArrayList<>();
    private ListView listView;
    private Button btnYes;
    private Button btnNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_finalize_ask);

        listView = (ListView) findViewById(R.id.list_view);
        btnYes = (Button) findViewById(R.id.button_affirmative);
        btnNo = (Button) findViewById(R.id.button_negative);
        getAllSubjects();
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

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStepState();
                Intent intentRepeat = new Intent(StepFinalizeAskActivity.this, WaitingForResultsActivity.class);
                finish();
                startActivity(intentRepeat);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBackProfile = new Intent(StepFinalizeAskActivity.this, StepReviewAskActivity.class);
                startActivity(intentBackProfile);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intentForward = new Intent(StepFinalizeAskActivity.this, StepExcludedOptionalAskActivity.class);
        finish();
        startActivity(intentForward);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
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
    public void updateStepState(){
        for (Subject subject : subjects){
            if (subject.getStatus() != SubjectStatus.NOT_ATTENDED){
                Session.getSubjectManager().removeFromUpdated(subject);
            }
        }
    }
    public void getAllSubjects(){
        subjects.addAll(Session.getSubjectManager().getUpdatedSubjects());
    }
    public void populate(){
        ArrayAdapter<Subject> adapter = new StepFinalizeAskActivity.SubjectListAdapter();
        listView.setAdapter(adapter);
    }
    private class SubjectListAdapter extends ArrayAdapter<Subject> {


        public SubjectListAdapter(){
            super(StepFinalizeAskActivity.this, R.layout.list_item_subjects_status, subjects);
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
            changeListItemColor(view, subject, status.getCode());
            return view;
        }
    }
}
