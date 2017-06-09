package com.example.stefany.paradigmas20171.infrastructure;


public enum SubjectStatus {
    APPROVED("Aprovado"),
    DISAPPROVED("Reprovado"),
    STUDYING("Cursando"),
    NOT_ATTENDED("Não cursado"),
    TO_ATTEND("Cursar"),
    TO_NOT_ATTEND("Não cursar");

    private String description;

    SubjectStatus(String description){
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
