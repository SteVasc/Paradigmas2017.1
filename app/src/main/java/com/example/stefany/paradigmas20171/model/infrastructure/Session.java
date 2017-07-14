package com.example.stefany.paradigmas20171.model.infrastructure;


import android.content.Context;

import java.util.Calendar;

public class Session {
    private static boolean logged;
    private static String email;
    private static String password;
    private static int semesters;
    private static String serverAddress;
    private static Context context;
    private static SubjectManager subjectManager;
    private static int currentEntry = 1;

    public static void setPeriods(int admissionYear, int entry) {
        int actualYear = Calendar.getInstance().get(Calendar.YEAR);
        int years = actualYear - admissionYear;
        if (admissionYear == actualYear){
            semesters = 1;
        } else if (currentEntry == entry){
            semesters = years*2;
        } else if (currentEntry < entry){
            semesters = (years*2) - 1;
        } else {
            semesters = (years*2) + 1;
        }
    }
    public static void adjustPeriods(int numberOfPeriods){
        semesters = semesters - numberOfPeriods;
    }

    public static int getSemesters() {
        return semesters;
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

    public static String getServerAddress() {
        return serverAddress;
    }

    public static void setServerAddress(String serverAddress) {
        Session.serverAddress = serverAddress;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setEmail(String email) {
        Session.email = email;
    }

    public static void setPassword(String password) {
        Session.password = password;
    }

    public static SubjectManager getSubjectManager() {
        return subjectManager;
    }

    public static Context getContext() {
        return context;
    }
}
