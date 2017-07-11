package com.example.stefany.paradigmas20171.model.infrastructure;

import com.example.stefany.paradigmas20171.model.local_database.SubjectDAO;

import java.util.ArrayList;

public class SubjectManager {

    private ArrayList<Period> periods = new ArrayList<>();
    private ArrayList<Subject> updatedSubjecs = new ArrayList<>();
    private SubjectDAO dao;

    public SubjectManager(){
        dao = SubjectDAO.getInstace();
    }

    public Period getPeriod(int periodNumber){
        Period period = null;
        for (Period p : periods){
            if (p.getPeriodNumber() == periodNumber){
                period = p;
            }
        }
        if (period == null){
            period = new Period(periodNumber);
            periods.add(period);
        }
        return period;
    }

    public void updateSubject(Subject subject, SubjectStatus status){
        Period period = getPeriod(subject.getPeriod());
        for (Subject s : period.getSubjects()){
            if (s.getDescription().equals(s.getDescription())){
                s.setStatus(status);
                updatedSubjecs.add(s);
            }
        }
    }

    public ArrayList<Subject> getUpdatedSubjecs() {
        return updatedSubjecs;
    }
}
