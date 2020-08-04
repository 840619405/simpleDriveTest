package com.tqc.hnkj.drivingtest.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager {
    public static SQLiteDatabase getSQLiteDataBase(SQLiteOpenHelper sqLiteOpenHelper){
        return sqLiteOpenHelper.getReadableDatabase();
    }
}
