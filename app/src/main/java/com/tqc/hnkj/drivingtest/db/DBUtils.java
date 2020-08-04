package com.tqc.hnkj.drivingtest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.CalendarView;

import com.tqc.hnkj.drivingtest.entity.TestEntity;

import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    public static int executeAdd(SQLiteDatabase db, String table, ContentValues cv){
        return (int) db.insert(table,null,cv);
    }
    public static Cursor executeQuery(SQLiteDatabase db, String table, String selection, String[] selectionArgs, String orderBy){
        return db.query(table, null, selection, selectionArgs, null, null, orderBy, null);
    }
    public static int executeDelete(SQLiteDatabase db,String table,String whereClause, String[] whereArgs ){
        return db.delete(table, whereClause, whereArgs);
    }
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

    public static List<TestEntity.ResultBean> queryTopic(Context context,String table,String selection,String[] selectionArgs,String orderBy){
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("topic.db",0,null);
        Cursor topic_questions = executeQuery(sqLiteDatabase, "topic_questions", selection, selectionArgs, orderBy);
        List<TestEntity.ResultBean> resultBeans=new ArrayList<>();
        Log.i("TAG", "queryTopic: "+topic_questions.getColumnCount());
        while (topic_questions.moveToNext()){
            Log.i("TAG", "while: ");
            TestEntity.ResultBean tr=new TestEntity.ResultBean();
            tr.setQuestion(topic_questions.getString(2));
            tr.setAnswer(topic_questions.getString(3));
            tr.setItem1(topic_questions.getString(4));
            tr.setItem2(topic_questions.getString(5));
            tr.setItem3(topic_questions.getString(6));
            tr.setItem4(topic_questions.getString(7));
            tr.setExplains(topic_questions.getString(8));
            tr.setUrl(topic_questions.getString(9));
            resultBeans.add(tr);
        }
        return resultBeans;
    }
}
