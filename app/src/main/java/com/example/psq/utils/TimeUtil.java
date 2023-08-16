package com.example.psq.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT2 = "yyyy-MM-dd HH:mm";

    /**
     * 获取当前时间
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }
}
