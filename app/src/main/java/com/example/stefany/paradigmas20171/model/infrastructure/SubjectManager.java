package com.example.stefany.paradigmas20171.model.infrastructure;

import com.example.stefany.paradigmas20171.model.local_database.SubjectDAO;

import java.util.ArrayList;

public class SubjectManager {

    private ArrayList<Period> periods = new ArrayList<>();
    private SubjectDAO dao;

    public SubjectManager(){
        dao = SubjectDAO.getInstace();
    }

    public void addPeriod(Period period){
        periods.add(period);
    }
    public void addPeriod(int periodNumber){
        Period period = new Period(periodNumber);
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

    public void removePeriod(Period period){
        periods.remove(period);
    }
}
