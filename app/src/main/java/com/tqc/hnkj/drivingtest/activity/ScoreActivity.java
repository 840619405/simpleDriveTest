package com.tqc.hnkj.drivingtest.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.tqc.hnkj.drivingtest.R;
import com.tqc.hnkj.drivingtest.adapter.ItemScoreAdapter;
import com.tqc.hnkj.drivingtest.db.TestDBHelper;
import com.tqc.hnkj.drivingtest.entity.ScoerEntity;
import com.tqc.hnkj.drivingtest.db.DBManager;
import com.tqc.hnkj.drivingtest.db.DBUtils;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView sc_iv_back;
    private PieChart sc_pie;
    private ListView sc_list;
    int subject;
    private List<ScoerEntity> list;
    int on;
    int off;
    private TextView sc_tv_bing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        initView();
        iniData();
        iniDb();
        iniPie();
        initListView();
    }
    private void iniPie() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getQualifier().equals("合格")) {
                off++;
            } else {
                on++;
            }
        }
        if (!list.isEmpty()) {
            sc_pie.setDescription("");
            sc_pie.setUsePercentValues(true);
            sc_pie.setDrawingCacheEnabled(true);
            sc_pie.setDrawHoleEnabled(false);
            List<String> x = new ArrayList<>();
            x.add("合格");
            x.add("不合格");
            List<Entry> y = new ArrayList<>();
            y.add(new Entry(off, 0));
            y.add(new Entry(on, 1));
            PieDataSet dataSet = new PieDataSet(y, "");
            dataSet.setValueFormatter(new PercentFormatter());
            int[] colors = {Color.parseColor("#66CCFF"), Color.parseColor("#EE5C42")};
            dataSet.setColors(colors);
            PieData pieData = new PieData(x, dataSet);
            sc_pie.setData(pieData);
        } else {
            sc_pie.setVisibility(View.GONE);
            sc_tv_bing.setVisibility(View.VISIBLE);
        }
    }
    /*
    String sql2="create table test_succ(_id integer primary key autoincrement," +
                "succ_subject text," +
                "succ_time text," +
                "succ_score text," +
                "succ_qualifier text," +
                "succ_diration text)";
     */

    private void iniDb() {
        list = new ArrayList<>();
        Cursor cursor = DBUtils.executeQuery(DBManager.getSQLiteDataBase(new TestDBHelper(this)), "test_succ", "succ_subject=?", new String[]{subject + ""}, "_id desc");
        while (cursor.moveToNext()) {
            ScoerEntity scoerEntity = new ScoerEntity();
            scoerEntity.setSubject(cursor.getString(1));
            scoerEntity.setTime(cursor.getString(2));
            scoerEntity.setScore(cursor.getString(3));
            scoerEntity.setQualifier(cursor.getString(4));
            scoerEntity.setDiration(cursor.getString(5));
            list.add(scoerEntity);
        }
    }
    private void iniData() {
        Intent intent = getIntent();
        subject = intent.getIntExtra("subject", 1);
    }
    public void initListView() {
        ItemScoreAdapter ia=new ItemScoreAdapter(this,list);
        sc_list.setAdapter(ia);
    }
    private void initView() {
        sc_iv_back = (ImageView) findViewById(R.id.sc_iv_back);
        sc_iv_back.setOnClickListener(this);
        sc_pie = (PieChart) findViewById(R.id.sc_pie);
        sc_list = (ListView) findViewById(R.id.sc_list);
        sc_tv_bing = (TextView) findViewById(R.id.sc_tv_bing);
        sc_tv_bing.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sc_iv_back:
                this.finish();
                break;
        }
    }
}
