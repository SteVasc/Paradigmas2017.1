package com.example.stefany.paradigmas20171.model.infrastructure;


public enum SubjectStatus {
    APPROVED("Aprovado", 1),
    DISAPPROVED("Reprovado", 2),
    STUDYING("Cursando", 3),
    NOT_ATTENDED("Não cursado", 4),
    TO_ATTEND("Cursar", 5),
    TO_NOT_ATTEND("Não cursar", 6);

    private String description;
    private int code;

    SubjectStatus(String description, int code){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
