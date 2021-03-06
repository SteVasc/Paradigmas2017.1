package com.example.stefany.paradigmas20171.view_control.results;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.model.infrastructure.Subject;
import com.example.stefany.paradigmas20171.view_control.access.ProfileActivity;

import java.util.ArrayList;

public class OptimizeResultsActivity extends AppCompatActivity {

    private GridView gridView;
    private static ArrayList<Subject> subjects;
    private static ArrayList<Subject> originalSubjects;
    private Subject[] subjectsArray;
    private static Integer semesterNumber;
    private ImageButton previous;
    private ImageButton next;
    private TextView txtSemester;
    private Button btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optmize_resuts);
        gridView = (GridView) findViewById(R.id.grid_view);
        txtSemester = (TextView) findViewById(R.id.txt_semester_number);
        previous = (ImageButton) findViewById(R.id.btn_previous_semester);
        next = (ImageButton) findViewById(R.id.btn_next_semester);
        btn_finish = (Button) findViewById(R.id.button_finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRepeat = new Intent(OptimizeResultsActivity.this, ProfileActivity.class);
                finish();
                startActivity(intentRepeat);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        txtSemester.setText("Periodo: " + semesterNumber.toString());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (semesterNumber < getMaxPeriod()){
                    setSemesterNumber(semesterNumber + 1);
                    setOriginalSubjects(originalSubjects);
                    Intent intentRepeat = new Intent(OptimizeResultsActivity.this, OptimizeResultsActivity.class);
                    finish();
                    startActivity(intentRepeat);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (semesterNumber > getStartingSemester()){
                    OptimizeResultsActivity.setStartingSemester();
                    setOriginalSubjects(originalSubjects);
                    Intent intentRepeat = new Intent(OptimizeResultsActivity.this, OptimizeResultsActivity.class);
                    finish();
                    startActivity(intentRepeat);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                }
            }
        });
        getSemesterSubjects();
        allocate();
        gridView.setAdapter(new GridAdapter(subjectsArray));
    }

    private void getSemesterSubjects() {
        subjects = new ArrayList<>();
        for (Subject subject : originalSubjects){
            if (subject.getSemester() == semesterNumber){
                subjects.add(subject);
            }
        }
    }

    public static void setSemesterNumber(Integer semesterNumber) {
        OptimizeResultsActivity.semesterNumber = semesterNumber;
    }

    public static void setStartingSemester(){
        semesterNumber = getStartingSemester();
    }

    public static int getMaxPeriod(){
        int number = 0;
        for (Subject subject : originalSubjects){
            if (subject.getSemester() > number){
                number = subject.getSemester();
            }
        }
        return number;
    }

    public static int getStartingSemester(){
        int number = 10;
        for (Subject subject : originalSubjects){
            if (subject.getSemester() < number){
                number = subject.getSemester();
            }
        }
        return number;
    }

    public void allocate(){
        subjectsArray = new Subject[10];
        for (Subject subject : subjects){
            String[] parts = subject.getSchedule().split(";");
            for (int i = 0; i < parts.length; i++) {
                String schedule = parts[i];
                Subject s = new Subject();
                s.setDescription(subject.getDescription());
                s.setSchedule(schedule);
                subjectsArray[getPosition(schedule)] = s;
            }
        }
    }

    public int getPosition(String schedule){
        int position = 0;
        String[] parts = schedule.split(" ");
        String day = parts[0];
        String time = parts[1];
        position = position + getColumn(day) + getRow(time);
        return position;
    }

    public int getColumn(String day){
        int rowAdd = 0;
        String[] days = new String[]{"SEG", "TER", "QUA", "QUI", "SEX"};
        for (int i = 0; i < days.length; i++){
            if (day.equals(days[i])){
                rowAdd = i;
                break;
            }
        }
        return rowAdd;
    }

    public int getRow(String time){
        int columnAdd = 0;
        if (time.contains("10-12")){
            columnAdd = 5;
        }
        return columnAdd;
    }


    public static void setOriginalSubjects(ArrayList<Subject> originalSubjects) {
        OptimizeResultsActivity.originalSubjects = originalSubjects;
    }

    private class GridAdapter extends BaseAdapter {

        private Subject[] subjects;

        public GridAdapter(Subject[] subjects){
            this.subjects = subjects;
        }

        @Override
        public int getCount() {
            return subjects.length;
        }

        @Override
        public Object getItem(int i) {
            return subjects[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.grid_view_item, viewGroup, false);
            }
            try {
                TextView subjectName = (TextView) view.findViewById(R.id.text_subject_name);
                subjectName.setText(subjects[i].getDescription());
                TextView subjectSchedule = (TextView) view.findViewById(R.id.text_subject_schedule);
                subjectSchedule.setText(subjects[i].getSchedule());
            } catch (Exception e){
                e.printStackTrace();
            }
            return view;
        }
    }
}
