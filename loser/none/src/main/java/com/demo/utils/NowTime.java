package com.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NowTime {
    public static String Now(){
        java.util.Date day=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       return  sdf.format(day);
    }
}
