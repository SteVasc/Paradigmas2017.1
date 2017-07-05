package com.example.stefany.paradigmas20171.model.local_database;

import android.database.Cursor;

import com.example.stefany.paradigmas20171.model.infrastructure.Subject;

import java.util.ArrayList;

public class SubjectDAO extends DAO {

    private static final SubjectDAO INSTACE = new SubjectDAO();

    private SubjectDAO(){};

    public static SubjectDAO getInstace(){
        return INSTACE;
    }

    public ArrayList<Subject> getSubjectsByPeriod(int period){
        ArrayList<Subject> subjects = new ArrayList<>();
        open();
        Cursor cursor = getDb().rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_SUBJECT +
                                    " WHERE " + DatabaseHelper.SUBJECT_PERIOD + " = ?",
                                    new String[]{String.valueOf(period)});

        if (cursor.moveToFirst()){
            do {
                Subject subject = new Subject();
                subject.setDescription(cursor.getString(1));
                subject.setPeriod(cursor.getInt(3));
                subjects.add(subject);
            } while (cursor.moveToNext());
        }
        return subjects;
    }
    public ArrayList<Subject> getOptionalSubjects(){
        ArrayList<Subject> subjects = new ArrayList<>();
        open();
        Cursor cursor = getDb().rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_SUBJECT +
                        " WHERE " + DatabaseHelper.SUBJECT_PERIOD + " = ?",
                new String[]{String.valueOf(0)});

        if (cursor.moveToFirst()){
            do {
                Subject subject = new Subject();
                subject.setDescription(cursor.getString(1));
                subject.setPeriod(cursor.getInt(3));
                subjects.add(subject);
            } while (cursor.moveToNext());
        }
        return subjects;
    }

}
