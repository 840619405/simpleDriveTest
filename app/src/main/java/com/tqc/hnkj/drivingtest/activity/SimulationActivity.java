package com.tqc.hnkj.drivingtest.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.tqc.hnkj.drivingtest.R;
import com.tqc.hnkj.drivingtest.adapter.ItemDialogAdapter;
import com.tqc.hnkj.drivingtest.db.TestDBHelper;
import com.tqc.hnkj.drivingtest.entity.TestEntity;
import com.tqc.hnkj.drivingtest.fragament.AnswerFragment;
import com.tqc.hnkj.drivingtest.db.DBManager;
import com.tqc.hnkj.drivingtest.db.DBUtils;
import com.tqc.hnkj.drivingtest.utils.GetTimeUtils;
import com.tqc.hnkj.drivingtest.utils.IsTypeUtils;
import com.tqc.hnkj.drivingtest.utils.NewsInterface;
import java.util.ArrayList;
import java.util.List;

public class SimulationActivity extends AppCompatActivity implements NewsInterface, View.OnClickListener {
    private ImageView sl_back;
    private TextView sl_time;
    private FrameLayout sl_framenlayout;
    private TextView sl_ok;
    private TextView sl_no;
    private TextView sl_entry;
    private ImageView sl_LieBiao;
    int subject;
    String model;
    String testType;
    AnswerFragment fragment;
    boolean isTime;
    int m;
    int s;
    String TAG = "TAG";
    GridView dialogGrid;
    private AlertDialog alertDialog;
    private View lieBiao;
    private View chengJi;
    private List<TestEntity.ResultBean> list;
    private ItemDialogAdapter ia;
    private List<Integer> unmArray;
    private Button btn_tiJiao;
    private Button dialogQuXiao,dialogQueRen;
    private TextView dialogHeGe,dialogDeFen,dialogDaDui,dialogDaCuo;
    private ImageView dialogTu;
    private AlertDialog alerChengji;
    ContentValues cv;
    private int nums;
    int unm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);
        initView();
        initData();
        initFragmnet();
        iniTime();
        initDialog();
        cv=new ContentValues();
    }

    private void iniTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isTime) {
                    try {
                        Thread.sleep(1000);
                        s++;
                        if (s == 60) {
                            m++;
                            s = 0;
                        }
                        sl_time.post(new Runnable() {
                            @Override
                            public void run() {
                                sl_time.setText(m + ":" + s);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void initData() {
        Intent intent = getIntent();
        subject = intent.getIntExtra("subject", 0);
        model = intent.getStringExtra("model");
        testType = intent.getStringExtra("testType");
    }

    private void initView() {
        sl_back = (ImageView) findViewById(R.id.sl_back);
        sl_time = (TextView) findViewById(R.id.sl_time);
        sl_framenlayout = (FrameLayout) findViewById(R.id.sl_framenlayout);
        sl_ok = (TextView) findViewById(R.id.sl_ok);
        sl_no = (TextView) findViewById(R.id.sl_no);
        sl_entry = (TextView) findViewById(R.id.sl_entry);
        sl_LieBiao = (ImageView) findViewById(R.id.sl_LieBiao);
        sl_ok.setText("0");
        sl_no.setText("0");
        LayoutInflater from = LayoutInflater.from(this);
        lieBiao = from.inflate(R.layout.dialog_liebiao, null);
        dialogGrid = lieBiao.findViewById(R.id.dialog_grid);
        chengJi =LayoutInflater.from(this).inflate(R.layout.dialog_chengji,null);
        dialogQuXiao=chengJi.findViewById(R.id.chengji_btn_quxiao);
        dialogQuXiao.setOnClickListener(this);
        dialogQueRen= chengJi.findViewById(R.id.chengji_btn_queren);
        dialogQueRen.setOnClickListener(this);
        dialogTu=chengJi.findViewById(R.id.chengji_tu);
        dialogDeFen=chengJi.findViewById(R.id.chengji_defen);
        dialogDaDui=chengJi.findViewById(R.id.chengji_dadui);
        dialogDaCuo=chengJi.findViewById(R.id.cheng_dacuo);
        dialogHeGe=chengJi.findViewById(R.id.chengji_hege);
        sl_LieBiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLieBiaoDialog();
            }
        });
        btn_tiJiao = (Button) findViewById(R.id.btn_tiJiao);
        btn_tiJiao.setOnClickListener(this);
        sl_back.setOnClickListener(this);
    }

    private void initFragmnet() {
        fragment = new AnswerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("subject", subject);
        bundle.putString("model", model);
        bundle.putString("testType", testType);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.sl_framenlayout, fragment);
        fragmentTransaction.commit();
    }

    private void initDialog() {
        unmArray = new ArrayList<>();
        unmArray.add(0);
        list = new ArrayList<>();
        ia = new ItemDialogAdapter(this, list, unmArray);
        dialogGrid.setAdapter(ia);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(lieBiao);
        alertDialog = builder.create();
        dialogGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setJump(position);
                alertDialog.dismiss();
            }
        });
        AlertDialog.Builder builder1=new AlertDialog.Builder(this);
        builder1.setView(chengJi);
        alerChengji = builder1.create();
    }

    private void setJump(int position) {
        ((AnswerFragment) fragment).setNum(position);
    }

    private void showLieBiaoDialog() {
        ia.notifyDataSetChanged();
        alertDialog.show();
    }
    private void showChengJiDialog(){
        nums = 0;
        for (int i = 0; i <list.size() ; i++) {
            if (list.get(i).isResult()){
                nums++;
            }
        }
        dialogDeFen.setText("本次考试:"+ nums +"分");
        dialogDaDui.setText("答对:"+sl_ok.getText().toString()+"题");
        dialogDaCuo.setText("答错"+sl_no.getText().toString()+"题");
        if (nums >=90){
            dialogHeGe.setText("合格");
            dialogTu.setImageResource(R.mipmap.icon_smile);
        }else {
            dialogHeGe.setText("不合格");
            dialogTu.setImageResource(R.mipmap.icon_cry);
        }
        alerChengji.show();
    }
    @Override
    public void onNextPage(int cun, int size) {
        sl_entry.setText(cun + "/" + size);
        unmArray.set(0, cun);
        unm=cun;
    }

    @Override
    public void onPreviousPage(int cun, int size) {
        sl_entry.setText(cun + "/" + size);
        unmArray.set(0, cun);
        unm=cun;
    }

    @Override
    public void onOKPlus() {
        String s = sl_ok.getText().toString();
        int i = Integer.parseInt(s);
        i++;
        sl_ok.setText(i + "");
        int test_err = DBUtils.executeDelete(DBManager.getSQLiteDataBase(new TestDBHelper(this)), "test_err", "err_explains=?",
                new String[]{list.get(unm - 1).getExplains()});
        if (test_err>0){

        }
    }

    @Override
    public void onNoPlus() {
        String s = sl_no.getText().toString();
        int i = Integer.parseInt(s);
        i++;
        sl_no.setText(i + "");
        TestEntity.ResultBean resultBean = list.get(unm - 1);
        Cursor test_err1 = DBUtils.executeQuery(DBManager.getSQLiteDataBase(new TestDBHelper(this)), "test_err", "err_explains=?",
                new String[]{resultBean.getExplains()}, null);
        if (!test_err1.moveToNext()) {
            cv.clear();
            cv.put("err_subject",subject);
            cv.put("err_type", IsTypeUtils.IsTypeUtils(resultBean));
            cv.put("err_time", GetTimeUtils.getTime());
            cv.put("err_answer",resultBean.getAnswer());
            cv.put("err_item1",resultBean.getItem1());
            cv.put("err_item2",resultBean.getItem2());
            cv.put("err_item3",resultBean.getItem3());
            cv.put("err_item4",resultBean.getItem4());
            cv.put("err_explains",resultBean.getExplains());
            cv.put("err_url",resultBean.getUrl());
            int test_err = DBUtils.executeAdd(DBManager.getSQLiteDataBase(new TestDBHelper(this)), "test_err", cv);
            if (test_err>0){

            }
        }
        test_err1.close();
    }

    @Override
    public void onArray(List<TestEntity.ResultBean> list) {
        this.list.clear();
        this.list.addAll(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isTime = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tiJiao:
                for (int i = 0; i <list.size() ; i++) {
                    if (!list.get(i).isState()){
                        setJump(i);
                        Toast.makeText(this, "还有题目没选择答案，不能提交", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                showChengJiDialog();
                break;
            case R.id.sl_back:
                SimulationActivity.this.finish();
                break;
            case R.id.chengji_btn_queren:
                Toast.makeText(this, "提交成功，可以去我的成绩查看历史成绩", Toast.LENGTH_SHORT).show();
                SimulationActivity.this.finish();
                cv.clear();
                cv.put("succ_subject",subject);
                cv.put("succ_time", GetTimeUtils.getTime());
                cv.put("succ_score",nums);
                if (nums>=90){
                    cv.put("succ_qualifier","合格");
                }else {
                    cv.put("succ_qualifier","不合格");
                }
                cv.put("succ_diration",sl_time.getText().toString());
                int test_succ = DBUtils.executeAdd(DBManager.getSQLiteDataBase(new TestDBHelper(this)), "test_succ", cv);
                if (test_succ>0){

                }
                break;
            case R.id.chengji_btn_quxiao:
                alerChengji.dismiss();
                break;
        }
    }
}
