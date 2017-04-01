package com.example.geeo.elevetest;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.id;

/**
 * Created by geeo on 01/04/17.
 */

public class DBManager extends SQLiteOpenHelper {


    public static final String TABLE_NAME  = "COMMENTS";




    public DBManager (Context context){
        super(context, TABLE_NAME, null, 0);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
