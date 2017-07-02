package com.example.stefany.paradigmas20171.model.infrastructure;

import java.util.ArrayList;

public class SubjectManager {

    private ArrayList<Period> periods = new ArrayList<>();

    public void addPeriod(Period period){
        periods.add(period);
    }

    public Period getPeriod(int periodNumber){
        Period period = null;
        for (Period p : periods){
            if (p.getPeriodNumber() == periodNumber){
                period = p;
            }
        }
        return period;
    }

    public void removePeriod(Period period){
        periods.remove(period);
    }

}
