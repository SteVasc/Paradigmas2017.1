package com.example.stefany.paradigmas20171.model.infrastructure;


import android.content.Context;

import java.util.Calendar;

public class Session {
    private static boolean logged;
    private static int periods;
    private static Context context;
    private static SubjectManager subjectManager;
    private static int currentEntry = 1;

    public static void setPeriods(int admissionYear, int entry) {
        int actualYear = Calendar.getInstance().get(Calendar.YEAR);
        int years = actualYear - admissionYear;
        if (currentEntry == entry){
            periods = years*2;
        } else if (currentEntry < entry){

            periods = years*2;
        }
    }
    public static int getPeriods() {
        return periods;
    }

    public static void setLogged(boolean logged) {
        Session.logged = logged;
    }

    public static boolean isLogged() {
        return logged;
    }

    public static void setContext(Context context) {
        Session.context = context;
    }

    public static void setSubjectManager(SubjectManager subjectManager) {
        Session.subjectManager = subjectManager;
    }

    public static SubjectManager getSubjectManager() {
        return subjectManager;
    }

    public static Context getContext() {
        return context;
    }
}
