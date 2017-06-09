package com.example.stefany.paradigmas20171.infrastructure;


import java.util.Calendar;

public class Session {
    private static boolean logged;
    private static int periods;

    public static void setPeriods(int admissionYear, boolean sameEntry) {
        int actualYear = Calendar.getInstance().get(Calendar.YEAR);
        int years = actualYear - admissionYear;
        if (sameEntry){
            periods = years*2;
        } else {
            //TODO implement logic of entries difference
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
}
