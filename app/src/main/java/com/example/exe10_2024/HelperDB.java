package com.example.exe10_2024;

import static com.example.exe10_2024.GradeDB.ACTIVEST;
import static com.example.exe10_2024.GradeDB.GRADE;
import static com.example.exe10_2024.GradeDB.QUARTER;
import static com.example.exe10_2024.GradeDB.STUDENT_ID;
import static com.example.exe10_2024.GradeDB.STUDENT_NAME;
import static com.example.exe10_2024.GradeDB.SUBJECT;
import static com.example.exe10_2024.GradeDB.TABLE_GRADES;
import static com.example.exe10_2024.GradeDB.TYPE;
import static com.example.exe10_2024.StudentDB.ACTIVE;
import static com.example.exe10_2024.StudentDB.ADDRESS;
import static com.example.exe10_2024.StudentDB.DAD_NAME;
import static com.example.exe10_2024.StudentDB.DAD_PHONE;
import static com.example.exe10_2024.StudentDB.HOME_PHONE;
import static com.example.exe10_2024.StudentDB.ID;
import static com.example.exe10_2024.StudentDB.MOM_NAME;
import static com.example.exe10_2024.StudentDB.MOM_PHONE;
import static com.example.exe10_2024.StudentDB.NAME;
import static com.example.exe10_2024.StudentDB.PHONE;
import static com.example.exe10_2024.StudentDB.TABLE_STUDENTS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class HelperDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "stgrdb.db";
    private static final int DATABASE_VERSION = 1;
    private String strCreate, strDelete;
    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        strCreate="CREATE TABLE "+TABLE_STUDENTS;
        strCreate+=" ("+StudentDB.KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+ID+" TEXT,";
        strCreate+=" "+NAME+" TEXT,";
        strCreate+=" "+ACTIVE+" INTEGER,";
        strCreate+=" "+ADDRESS+" TEXT,";
        strCreate+=" "+PHONE+" TEXT,";
        strCreate+=" "+HOME_PHONE+" TEXT,";
        strCreate+=" "+DAD_NAME+" TEXT,";
        strCreate+=" "+DAD_PHONE+" TEXT,";
        strCreate+=" "+MOM_NAME+" TEXT,";
        strCreate+=" "+MOM_PHONE+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+TABLE_GRADES;
        strCreate+=" ("+GradeDB.KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+ACTIVEST+" INTEGER,";
        strCreate+=" "+STUDENT_ID+" TEXT,";
        strCreate+=" "+STUDENT_NAME+" TEXT,";
        strCreate+=" "+SUBJECT+" TEXT,";
        strCreate+=" "+TYPE+" TEXT,";
        strCreate+=" "+GRADE+" INTEGER,";
        strCreate+=" "+QUARTER+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        strDelete="DROP TABLE IF EXISTS "+TABLE_STUDENTS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+TABLE_GRADES;
        db.execSQL(strDelete);

        onCreate(db);


    }
}
