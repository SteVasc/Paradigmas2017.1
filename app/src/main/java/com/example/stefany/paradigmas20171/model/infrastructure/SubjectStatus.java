package com.example.stefany.paradigmas20171.model.infrastructure;


public enum SubjectStatus {
    APPROVED("Aprovado", 1),
    DISAPPROVED("Reprovado", 2),
    STUDYING("Cursando", 3),
    NOT_ATTENDED("Não cursado", 0),
    TO_ATTEND("Cursar", 4),
    TO_NOT_ATTEND("Não cursar", 5);

    private String description;
    private int code;

    SubjectStatus(String description, int code){
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}
