package com.example.stefany.paradigmas20171.model.infrastructure;



import com.example.stefany.paradigmas20171.model.local_database.SubjectDAO;

import java.util.ArrayList;

public class Semester {

    private ArrayList<Subject> subjects = new ArrayList<>();
    private Integer periodNumber;
    private SubjectDAO dao;

    public Semester(Integer periodNumber){
        this.periodNumber = periodNumber;
        if (periodNumber > 0){
            dao = SubjectDAO.getInstace();
            dao.setUp(Session.getContext());
            subjects = dao.getSubjectsByPeriod(periodNumber);
        } else {
            dao = SubjectDAO.getInstace();
            dao.setUp(Session.getContext());
            subjects = dao.getOptionalSubjects();
        }
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public Integer getPeriodNumber() {
        return periodNumber;
    }
}
