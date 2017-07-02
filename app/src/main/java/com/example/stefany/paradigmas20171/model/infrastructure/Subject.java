package com.example.stefany.paradigmas20171.model.infrastructure;

public class Subject {
    private String description;
    private SubjectStatus status;

    public Subject(){
        status = SubjectStatus.NOT_ATTENDED;
    }

    public String getDescription() {
        return description;
    }

    public SubjectStatus getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(SubjectStatus status) {
        this.status = status;
    }
}
