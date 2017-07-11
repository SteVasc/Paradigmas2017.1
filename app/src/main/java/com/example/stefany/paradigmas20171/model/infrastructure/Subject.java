package com.example.stefany.paradigmas20171.model.infrastructure;

public class Subject {
    private String description;
    private SubjectStatus status;
    private SubjectStatus inclusionStatus;
    private int period;

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

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(SubjectStatus status) {
        this.status = status;
    }
}
