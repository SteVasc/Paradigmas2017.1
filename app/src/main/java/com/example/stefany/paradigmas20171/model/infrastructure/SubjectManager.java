package com.example.stefany.paradigmas20171.model.infrastructure;

import java.util.ArrayList;

public class SubjectManager {

    private ArrayList<Period> periods = new ArrayList<>();
    private ArrayList<Subject> updatedSubjects = new ArrayList<>();
    private ArrayList<Subject> excludedSubjects = new ArrayList<>();

    public SubjectManager(){
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

    public void updateSubject(Subject subject){
        Period period = getPeriod(subject.getPeriod());
        for (Subject s : period.getSubjects()){
            if (s.getDescription().equals(subject.getDescription())){
                if (!checkIfUpdated(s)) {
                    updatedSubjects.add(s);
                }
            }
        }
    }

    private boolean checkIfUpdated(Subject subject){
        boolean exists = false;
        for (Subject s : updatedSubjects){
            if (s.getDescription().equals(subject.getDescription())){
                exists = true;
            }
        }
        return exists;
    }

    private boolean checkIfExcluded(Subject subject){
        boolean exists = false;
        for (Subject s : excludedSubjects){
            if (s.getDescription().equals(subject.getDescription())){
                exists = true;
            }
        }
        return exists;
    }

    public void excludeSubject(Subject subject){
        Period period = getPeriod(subject.getPeriod());
        for (Subject s : period.getSubjects()){
            if (s.getDescription().equals(subject.getDescription())){
                if (!checkIfExcluded(s)) {
                    excludedSubjects.add(s);
                }
            }
        }
    }

    public void removeFromUpdated(Subject subject){
        for (Subject s : updatedSubjects){
            if (s.getDescription().equals(subject.getDescription())){
                updatedSubjects.remove(s);
                break;
            }
        }
    }

    public ArrayList<Subject> getUpdatedSubjects() {
        return updatedSubjects;
    }
}
