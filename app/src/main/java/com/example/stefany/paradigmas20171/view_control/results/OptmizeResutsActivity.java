package com.example.stefany.paradigmas20171.view_control.results;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.model.infrastructure.Subject;

import java.util.ArrayList;

public class OptmizeResutsActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<Subject> subjects;
    private Subject[] subjectsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optmize_resuts);
        gridView = (GridView) findViewById(R.id.grid_view);
        testSubjects();
        allocate();
        byPassAllocate();
        gridView.setAdapter(new GridAdapter(this, subjectsArray));
    }

    public void byPassAllocate(){
        subjectsArray = new Subject[10];
        Subject s = subjects.get(1);
        s.setSchedule("SEG 08-10");
        subjectsArray[0] = s;
        s = subjects.get(4);
        s.setSchedule("TER 08-10");
        subjectsArray[1] = s;
        s = subjects.get(1);
        s.setSchedule("QUA 08-10");
        subjectsArray[2] = s;
        s = subjects.get(3);
        s.setSchedule("QUI 08-10");
        subjectsArray[3] = s;
        s = subjects.get(3);
        s.setSchedule("SEX 08-10");
        subjectsArray[4] = s;
        s = subjects.get(0);
        s.setSchedule("SEG 10-12");
        subjectsArray[5] = s;
        s = subjects.get(3);
        s.setSchedule("TER 10-12");
        subjectsArray[6] = s;
        s = subjects.get(0);
        s.setSchedule("QUA 10-12");
        subjectsArray[7] = s;
        s = subjects.get(2);
        s.setSchedule("QUI 10-12");
        subjectsArray[8] = s;
        s = subjects.get(4);
        s.setSchedule("SEX 10-12");
        subjectsArray[9] = s;
    }

    public void allocate(){
        subjectsArray = new Subject[10];
        for (Subject subject : subjects){
            String[] parts = subject.getSchedule().split(";");
            for (int i = 0; i < parts.length; i++) {
                String schedule = parts[i];
                Subject s = subject;
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
        position = position + getRow(time) + getColumn(day);
        return position;
    }

    public int getRow(String time){
        int rowAdd = 0;
        Log.d("TIMEOFSCHEDULE", time);
        String[] days = new String[]{"SEG", "TER", "QUA", "QUI", "SEX"};
        for (int i = 0; i < days.length; i++){
            if (time.contains(days[i])){
                rowAdd = i;
                break;
            }
        }
        return rowAdd;
    }

    public int getColumn(String day){
        int columnAdd = 0;
        Log.d("DAYOFSCHEDULE", day);
        if (day.contains("10-12")){
            columnAdd = 5;
        }
        return columnAdd;
    }

    public void testSubjects(){
        subjects = Session.getSubjectManager().getPeriod(1).getSubjects();
        subjects.get(0).setSchedule("SEG 10-12;QUA 10-12");
        subjects.get(1).setSchedule("SEG 08-10;QUA 08-10");
        subjects.get(2).setSchedule("QUI 10-12");
        subjects.get(3).setSchedule("TER 10-12;QUI 08-10;SEX 08-10");
        subjects.get(4).setSchedule("TER 08-10;SEX 10-12");
    }

    private class GridAdapter extends BaseAdapter {

        private Subject[] subjects;
        private Context context;

        public GridAdapter(Context context, Subject[] subjects){
            this.subjects = subjects;
            this.context = context;
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
