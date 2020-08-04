package com.tqc.hnkj.drivingtest.utils;

import com.tqc.hnkj.drivingtest.entity.TestEntity;

public class IsTypeUtils {
    public static String IsTypeUtils(TestEntity.ResultBean resultBean){
        if (resultBean.getItem3().equals("")){
            return "判断题";
        }else if (!resultBean.getUrl().equals("")){
            return "图表题";
        }else {
            return "文字题";
        }
    }
}