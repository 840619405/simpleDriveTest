package com.tqc.hnkj.drivingtest.utils;

import com.tqc.hnkj.drivingtest.entity.TestEntity;

import java.util.List;

public interface NewsInterface {
     void onNextPage(int cun,int size);//下一页
     void onPreviousPage(int cun,int size);//上一页
     void onOKPlus();//做对
     void onNoPlus();//做错
     void onArray(List<TestEntity.ResultBean> list);//返回数据源
}
