package com.tqc.hnkj.drivingtest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

public class TopicDBHelper extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;
    private static String DB_PATH = "/data/data/com.tqc.hnkj.drivingtest/databases";
    private static String DB_NAME = "topic.db";
    public TopicDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        String sql="create table topic_questions(_id integer primary key autoincrement," +
                "subject text,"+
                "question text," +
                "answer text," +
                "item1 text," +
                "item2 text," +
                "item3 text," +
                "item4 text," +
                "explains text," +
                "url text)";
        db.execSQL(sql);
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
