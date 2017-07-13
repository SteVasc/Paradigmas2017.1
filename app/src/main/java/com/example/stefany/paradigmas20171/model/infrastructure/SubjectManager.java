package com.example.stefany.paradigmas20171.model.infrastructure;

import java.util.ArrayList;

public class SubjectManager {

    private ArrayList<Semester> semesters = new ArrayList<>();
    private ArrayList<Subject> mySubjects = new ArrayList<>();
    private ArrayList<Subject> updatedSubjects = new ArrayList<>();
    private ArrayList<Subject> excludedSubjects = new ArrayList<>();

    public SubjectManager(){
    }

    public Semester getPeriod(int periodNumber){
        Semester semester = null;
        for (Semester p : semesters){
            if (p.getPeriodNumber() == periodNumber){
                semester = p;
            }
        }
        if (semester == null){
            semester = new Semester(periodNumber);
            semesters.add(semester);
        }
        return semester;
    }

    public Subject getSubjectByCode(String code){
        ArrayList<Subject> allSubjects = new ArrayList<>();
        Subject s = null;
        for (int i = 1; i <= 10;i++){
            allSubjects.addAll(Session.getSubjectManager().getPeriod(i).getSubjects());
        }
        for (Subject subject : allSubjects){
            String subjectCode = subject.getCode();
            if (subjectCode.equals(code)){
                s = subject;
                break;
            }
        }
        return s;
    }

    public void updateSubject(Subject subject){
        Semester semester = getPeriod(subject.getSemester());
        for (Subject s : semester.getSubjects()){
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
        Semester semester = getPeriod(subject.getSemester());
        for (Subject s : semester.getSubjects()){
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

    public void setMySubjects(ArrayList<Subject> mySubjects) {
        this.mySubjects = mySubjects;
    }

    public ArrayList<Subject> getMySubjects() {
        return mySubjects;
    }
}
