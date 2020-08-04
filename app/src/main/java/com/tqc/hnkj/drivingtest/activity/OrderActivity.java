package com.tqc.hnkj.drivingtest.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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

public class OrderActivity extends AppCompatActivity implements NewsInterface {
    private FrameLayout orderFramenlayout;
    private TextView tvOrderOk;
    private TextView tvOrderNo;
    private ImageView ivOrderLieBiao;
    int subject;
    String model;//测试类型
    String testType;//测试科目
    public Fragment fragment;
    private TextView order_tv_entry;
    private View view;
    int unm;
    int size;
    GridView dialogGrid;
    List<TestEntity.ResultBean> list;
    private AlertDialog.Builder builder;
    private ItemDialogAdapter ia;
    final String TAG = "TAG";
    List<Integer> unmArray;
    private AlertDialog alertDialog;
    private ImageView iv_back;
    ContentValues cv;
    int code;
    String errType;
    private TextView tv_tile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();//初始化控件
        initDialog();//初始化对话框
        initData();//初始化数据
        initFragmnet();//初始化fragment
    }

    private void initFragmnet() {
        fragment = new AnswerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("subject", subject);
        bundle.putString("model", model);
        bundle.putString("testType", testType);
        bundle.putInt("code", code);
        bundle.putString("errType", errType);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.order_framenlayout, fragment);
        fragmentTransaction.commit();
    }

    private void initData() {
        Intent intent = getIntent();
        subject = intent.getIntExtra("subject", 0);
        model = intent.getStringExtra("model");
        testType = intent.getStringExtra("testType");
        code = intent.getIntExtra("code", 0);
        errType = intent.getStringExtra("errType");
        if (code==2){
            tv_tile.setText("我的错题");
        }
    }

    private void initView() {
        orderFramenlayout = (FrameLayout) findViewById(R.id.order_framenlayout);
        tvOrderOk = (TextView) findViewById(R.id.tv_order_ok);
        tvOrderNo = (TextView) findViewById(R.id.tv_order_no);
        ivOrderLieBiao = (ImageView) findViewById(R.id.iv_order_LieBiao);
        order_tv_entry = (TextView) findViewById(R.id.order_tv_entry);
        tvOrderOk.setText("0");
        tvOrderNo.setText("0");
        LayoutInflater from = LayoutInflater.from(this);
        view = from.inflate(R.layout.dialog_liebiao, null);
        dialogGrid = view.findViewById(R.id.dialog_grid);
        ivOrderLieBiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.this.finish();
            }
        });
        cv = new ContentValues();
        tv_tile = (TextView) findViewById(R.id.tv_tile);
        if (code>0){
            tv_tile.setText("我的错题");
        }
    }

    private void initDialog() {
        unmArray = new ArrayList<>();
        unmArray.add(0);
        list = new ArrayList<>();
        ia = new ItemDialogAdapter(this, list, unmArray);
        dialogGrid.setAdapter(ia);
        builder = new AlertDialog.Builder(this);
        builder.setView(view);
        alertDialog = builder.create();
        dialogGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((AnswerFragment) fragment).setNum(position);
                alertDialog.dismiss();
                unm=position;
            }
        });
    }

    /*
    显示列表对话框
     */
    public void showDialog() {
        ia.notifyDataSetChanged();
        Integer integer = unmArray.get(0);
        int entry = integer;
        dialogGrid.smoothScrollToPositionFromTop(entry, 0);
        alertDialog.show();
    }

    /*
    fragment下一页回调接口
     */
    @Override
    public void onNextPage(int cun, int size) {
        order_tv_entry.setText(cun + "/" + size);
        unm = cun;
        unmArray.set(0, unm - 1);
        this.size = size;
    }

    /*
    fragment上一页回调接口
     */
    @Override
    public void onPreviousPage(int cun, int size) {
        order_tv_entry.setText(cun + "/" + size);
        unm = cun;
        unmArray.set(0, unm);
        this.size = size;
    }

    /*
    fragment答题对了回调接口
     */
    @Override
    public void onOKPlus() {
        int i = Integer.parseInt(tvOrderOk.getText() + "");
        i++;
        tvOrderOk.setText((i) + "");
        int test_err = DBUtils.executeDelete(DBManager.getSQLiteDataBase(new TestDBHelper(this)), "test_err", "err_explains=?",
                new String[]{list.get(unm - 1).getExplains()});
        if (test_err > 0) {
        }
    }

    /*
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
     */
    /*
    fragment答题错了回调接口
     */
    @Override
    public void onNoPlus() {
        int i = Integer.parseInt(tvOrderNo.getText() + "");
        i++;
        tvOrderNo.setText(i + "");
        TestEntity.ResultBean resultBean = list.get(unm - 1);
        Cursor test_err1 = DBUtils.executeQuery(DBManager.getSQLiteDataBase(new TestDBHelper(this)), "test_err", "err_explains=?",
                new String[]{resultBean.getExplains()}, null);
        if (!test_err1.moveToNext()) {
            IsTypeUtils.IsTypeUtils(resultBean);
            cv.clear();
            cv.put("err_type", IsTypeUtils.IsTypeUtils(resultBean));
            cv.put("err_subject", subject);
            cv.put("err_time", GetTimeUtils.getTime());
            cv.put("err_answer", resultBean.getAnswer());
            cv.put("err_item1", resultBean.getItem1());
            cv.put("err_item2", resultBean.getItem2());
            cv.put("err_item3", resultBean.getItem3());
            cv.put("err_item4", resultBean.getItem4());
            cv.put("err_explains", resultBean.getExplains());
            cv.put("err_url", resultBean.getUrl());
            int test_err = DBUtils.executeAdd(DBManager.getSQLiteDataBase(new TestDBHelper(this)), "test_err", cv);
            if (test_err > 0) {
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}
