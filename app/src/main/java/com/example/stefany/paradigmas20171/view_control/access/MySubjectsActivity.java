package com.example.stefany.paradigmas20171.view_control.access;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.model.infrastructure.Subject;
import com.example.stefany.paradigmas20171.model.infrastructure.SubjectStatus;

import java.util.ArrayList;

public class MySubjectsActivity extends AppCompatActivity {

    private static ArrayList<Subject> subjects;
    private ListView listView;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subjects);
        listView = (ListView) findViewById(R.id.list_view);
        btnBack = (Button) findViewById(R.id.button_back);
        subjects = Session.getSubjectManager().getMySubjects();
        populateList();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBackProfile = new Intent(MySubjectsActivity.this, ProfileActivity.class);
                startActivity(intentBackProfile);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intentForward = new Intent(MySubjectsActivity.this, ProfileActivity.class);
        finish();
        startActivity(intentForward);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
    public void populateList(){
        ArrayAdapter<Subject> adapter = new MySubjectsActivity.SubjectListAdapter();
        listView.setAdapter(adapter);
    }
    private class SubjectListAdapter extends ArrayAdapter<Subject> {
        public SubjectListAdapter(){
            super(MySubjectsActivity.this, R.layout.list_item_subjects_status, subjects);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View view, @NonNull ViewGroup parent) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.list_item_subjects_status, parent, false);
            }
            final Subject subject = subjects.get(position);
            TextView subjectName = (TextView) view.findViewById(R.id.text_subject_name);
            subjectName.setText(subject.getDescription());
            TextView subjectStatus = (TextView) view.findViewById(R.id.text_status);
            SubjectStatus status = subject.getStatus();
            subjectStatus.setText("Status: " + status.getDescription());
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            return view;
        }
    }
}
