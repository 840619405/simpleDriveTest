package com.tqc.hnkj.drivingtest.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tqc.hnkj.drivingtest.R;
import com.tqc.hnkj.drivingtest.adapter.ItemErrAdapter;
import com.tqc.hnkj.drivingtest.db.TestDBHelper;
import com.tqc.hnkj.drivingtest.entity.ErrEntity;
import com.tqc.hnkj.drivingtest.db.DBManager;
import com.tqc.hnkj.drivingtest.db.DBUtils;

import java.util.ArrayList;
import java.util.List;

public class ErrActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView err_back;
    private TextView err_num;
    private Button err_btn_qingKong;
    private Button err_btn_CuoTi;
    private ListView err_lv;
    private int subject;
    private int tuBiao;
    private int wenZi;
    private int panDuan;
    private List<ErrEntity> errArray;
    private ItemErrAdapter errAdapter;
    private SQLiteDatabase sqLiteDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_err);
        initView();
        initData();
        iniListView();
    }

    private void iniListView() {
        errArray = new ArrayList<>();
        errArray.add(new ErrEntity("图表题",tuBiao+""));
        errArray.add(new ErrEntity("文字题",wenZi+""));
        errArray.add(new ErrEntity("判断题",panDuan+""));
        errAdapter = new ItemErrAdapter(this, errArray);
        err_lv.setAdapter(errAdapter);
        err_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it=new Intent();
                it.putExtra("subject",subject);
                it.putExtra("code",2);
                switch (position){
                    case 0:
                        if (tuBiao!=0) {
                            it.putExtra("errType","图表题");
                            it.setClass(ErrActivity.this,OrderActivity.class);
                            ErrActivity.this.startActivity(it);
                        }
                        break;
                    case 1:
                        if (wenZi!=0) {
                            it.putExtra("errType","文字题");
                            it.setClass(ErrActivity.this,OrderActivity.class);
                            ErrActivity.this.startActivity(it);
                        }
                        break;
                    case 2:
                        if (panDuan!=0) {
                            it.putExtra("errType","判断题");
                            it.setClass(ErrActivity.this,OrderActivity.class);
                            ErrActivity.this.startActivity(it);
                        }
                        break;
                }
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        subject=intent.getIntExtra("subject",1);
        sqLiteDataBase = DBManager.getSQLiteDataBase(new TestDBHelper(this));
        Cursor test_err = DBUtils.executeQuery(sqLiteDataBase, "test_err", "err_subject=?", new String[]{subject + ""}, null);
        while (test_err.moveToNext()){
            String type = test_err.getString(2);
            switch (type){
                case "图表题":
                    tuBiao++;
                    break;
                case "文字题":
                    wenZi++;
                    break;
                case "判断题":
                    panDuan++;
                    break;
            }
        }
        err_num.setText((tuBiao+wenZi+panDuan)+"");
    }

    private void initView() {
        err_back = (ImageView) findViewById(R.id.err_back);
        err_back.setOnClickListener(this);
        err_num = (TextView) findViewById(R.id.err_num);
        err_btn_qingKong = (Button) findViewById(R.id.err_btn_qingKong);
        err_btn_CuoTi = (Button) findViewById(R.id.err_btn_CuoTi);
        err_lv = (ListView) findViewById(R.id.err_lv);
        err_btn_qingKong.setOnClickListener(this);
        err_btn_CuoTi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.err_btn_qingKong:
                int test_err = DBUtils.executeDelete(sqLiteDataBase, "test_err", "err_subject=?", new String[]{subject + ""});
                if (test_err>0) {
                    errArray.set(0,new ErrEntity("图表题","0"));
                    errArray.set(1,new ErrEntity("文字题","0"));
                    errArray.set(2,new ErrEntity("判断题","0"));
                    errAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "清空成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "清空失败，请稍后再试", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.err_btn_CuoTi:
                Intent it=new Intent();
                it.putExtra("subject",subject);
                it.putExtra("code",1);
                it.setClass(this,OrderActivity.class);
                this.startActivity(it);
                break;
            case R.id.err_back:
                this.finish();
                break;
        }
    }
}
