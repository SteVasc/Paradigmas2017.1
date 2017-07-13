package com.example.stefany.paradigmas20171.model.infrastructure;

public class Subject {
    private String description;
    private SubjectStatus status;
    private SubjectStatus inclusionStatus;
    private int semester;
    private String schedule;
    private String code;

    public Subject(){
        this.status = SubjectStatus.NOT_ATTENDED;
        this.inclusionStatus = SubjectStatus.TO_ATTEND;
    }

    public SubjectStatus getInclusionStatus() {
        return inclusionStatus;
    }

    public void setInclusionStatus(SubjectStatus inclusionStatus) {
        this.inclusionStatus = inclusionStatus;
    }

    public String getDescription() {
        return description;
    }

    public SubjectStatus getStatus() {
        return status;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(SubjectStatus status) {
        this.status = status;
    }

    public String getSchedule() {
        return schedule;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
