package com.gxy.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateForm {
    public static String getDateStr(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
