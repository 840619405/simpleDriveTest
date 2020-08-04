package com.tqc.hnkj.drivingtest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TestDBHelper extends SQLiteOpenHelper {
    public TestDBHelper(Context context) {
        super(context, "Test.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table test_err(_id integer primary key autoincrement," +
                "err_subject text," +
                "err_type text,"+
                "err_time text," +
                "err_question text," +
                "err_answer text," +
                "err_item1 text," +
                "err_item2 text," +
                "err_item3 text," +
                "err_item4 text," +
                "err_explains text," +
                "err_url text)";
        String sql2="create table test_succ(_id integer primary key autoincrement," +
                "succ_subject text," +
                "succ_time text," +
                "succ_score text," +
                "succ_qualifier text," +
                "succ_diration text)";
        db.execSQL(sql);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
