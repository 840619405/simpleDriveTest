package com.tqc.hnkj.drivingtest.fragament;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Toast;
import com.google.gson.Gson;
import com.jakewharton.disklrucache.DiskLruCache;
import com.tqc.hnkj.drivingtest.R;
import com.tqc.hnkj.drivingtest.adapter.ItemOrderAdapter;
import com.tqc.hnkj.drivingtest.db.TestDBHelper;
import com.tqc.hnkj.drivingtest.entity.TestEntity;
import com.tqc.hnkj.drivingtest.db.DBManager;
import com.tqc.hnkj.drivingtest.db.DBUtils;
import com.tqc.hnkj.drivingtest.utils.DiskLruCacheUtils;
import com.tqc.hnkj.drivingtest.utils.DownloadUrlToStream;
import com.tqc.hnkj.drivingtest.utils.NetUtil;
import com.tqc.hnkj.drivingtest.utils.NewsInterface;
import com.tqc.hnkj.drivingtest.utils.StringRequest;
import com.tqc.hnkj.drivingtest.view.ViewViewFlipper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnswerFragment extends Fragment {
    public ViewViewFlipper moreItemsListView;
    int subject;
    String model;
    String testType;
    ProgressDialog pm;
    String TAG = "TAG";
    ObjectAnimator left;
    ObjectAnimator right;
    ObjectAnimator empty;
    int screenWidth;
    private List<TestEntity.ResultBean> result1;
    private int screemHeight;
    private NewsInterface newsInterface;
    int code;
    SQLiteDatabase db;
    String errType;
    private ItemOrderAdapter oa;
    private DiskLruCacheUtils diskLruCacheUtils;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewsInterface){
            newsInterface=(NewsInterface)context;
        }else {
            throw new RuntimeException(context.toString()+"must implement OnFragmentInteractionListener");
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_answer, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moreItemsListView = (ViewViewFlipper) view.findViewById(R.id.fragment_item);
        initData();
        initAnimator();
        db= DBManager.getSQLiteDataBase(new TestDBHelper(getContext()));
        if (code==0) {
            initViewList();
        }else {
            iniCodeList();
        }
        //huanCun();
        //moreItemsListView.setDisplayedChild();
    }
    //接受跳转传值
    private void initData() {
        Bundle bundle = getArguments();
        subject = bundle.getInt("subject");
        model = bundle.getString("model");
        testType = bundle.getString("testType");
        errType=bundle.getString("errType");
        code=bundle.getInt("code",0);
    }
    //初始化动画
    private void initAnimator() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screemHeight = dm.heightPixels;
        Log.i(TAG, "initAnimator: " + screemHeight);
        left = ObjectAnimator.ofFloat(moreItemsListView, "translationX", 0, -screenWidth);
        right = ObjectAnimator.ofFloat(moreItemsListView, "translationX", 0, screenWidth);
        empty = ObjectAnimator.ofFloat(moreItemsListView, "translationX", 0, 0);
    }
    //初始化ListView
    private void initViewList() {
        pm = ProgressDialog.show(getContext(), "请稍等", "正在加载");
        result1= DBUtils.queryTopic(getContext(), "topic_questions", "subject=?", new String[]{subject + ""}, null);
        if (testType.equals("rand")){
            List<Integer> num=new ArrayList<>();
            List<TestEntity.ResultBean> list=new ArrayList<>();
            Random rm=new Random();
            for (int i = 0; i <100 ; i++) {
                int tmp=rm.nextInt(result1.size());
                while (num.contains(tmp)){
                    tmp=rm.nextInt(result1.size());
                }
                list.add(result1.get(tmp));
            }
            result1=list;
        }
        iniAdapter();
        pm.dismiss();
    }
    //初始化适配器
    private void iniAdapter() {
        diskLruCacheUtils = new DiskLruCacheUtils(getContext());
        oa = new ItemOrderAdapter(getActivity(), result1, moreItemsListView, left, right, empty, screenWidth, newsInterface,diskLruCacheUtils);
        moreItemsListView.setAdapter(oa);
        newsInterface.onNextPage(1, result1.size());
        newsInterface.onArray(result1);
        pm.dismiss();
    }
    //跳转题目
    public void setNum(int position){
        //moreItemsListView.setOutAnimation(empty);
        //moreItemsListView.setInAnimation(empty);
        moreItemsListView.setDisplayedChild(position);
        newsInterface.onNextPage(position+1,result1.size());
    }
    //根据code查找数据库
    private void iniCodeList(){
        pm = ProgressDialog.show(getContext(), "请稍等", "正在加载");
        Cursor test_err=null;
        result1=new ArrayList<>();
        switch (code){
            case 1:
                test_err = DBUtils.executeQuery(db, "test_err", null, null, null);
                break;
            case 2:
                test_err = DBUtils.executeQuery(db, "test_err", "err_type=?", new String[]{errType}, null);
                break;
        }
        while (test_err.moveToNext()){
            TestEntity.ResultBean resultBean=new TestEntity.ResultBean();
            resultBean.setQuestion(test_err.getString(4));
            resultBean.setAnswer(test_err.getString(5));
            resultBean.setItem1(test_err.getString(6));
            resultBean.setItem2(test_err.getString(7));
            resultBean.setItem3(test_err.getString(8));
            resultBean.setItem4(test_err.getString(9));
            resultBean.setExplains(test_err.getString(10));
            resultBean.setUrl(test_err.getString(11));
            result1.add(resultBean);
        }
        iniAdapter();
    }
}
