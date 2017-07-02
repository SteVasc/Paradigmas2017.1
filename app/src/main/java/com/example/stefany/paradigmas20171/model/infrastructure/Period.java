package com.example.stefany.paradigmas20171.model.infrastructure;



import java.util.ArrayList;

public class Period {

    private ArrayList<Subject> subjects = new ArrayList<>();
    private Integer periodNumber;

    public Period(Integer periodNumber){
        this.periodNumber = periodNumber;
        //TODO implement subject getter
    }

    public Integer getPeriodNumber() {
        return periodNumber;
    }
}
