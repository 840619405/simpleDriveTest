package com.tqc.hnkj.drivingtest.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTimeUtils {
    public static String getTime(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
