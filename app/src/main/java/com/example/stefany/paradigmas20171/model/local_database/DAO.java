package com.example.stefany.paradigmas20171.model.local_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class DAO {
    private static DatabaseHelper helper;
    private static SQLiteDatabase db;
    private Context context;

    public static DatabaseHelper getHelper() {
        return helper;
    }

    public static void setHelper(DatabaseHelper helper) {
        DAO.helper = helper;
    }

    public static SQLiteDatabase getDb() {
        return db;
    }

    public static void setDb(SQLiteDatabase db) {
        DAO.db = db;
    }
    public void setUp(Context context){
        this.context = context;
        helper = new DatabaseHelper(context);
    }
    public void open(){
        db = helper.getWritableDatabase();
    }
    public void close(){
        db.close();
    }
}
