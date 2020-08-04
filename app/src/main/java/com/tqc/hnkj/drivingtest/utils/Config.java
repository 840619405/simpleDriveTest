package com.tqc.hnkj.drivingtest.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by a on 2018/4/26.
 */

public class Config {
    public static final String URI="http://v.juhe.cn/jztk/query?";
    public static String gettime(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
