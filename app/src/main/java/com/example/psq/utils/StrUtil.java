package com.example.psq.utils;

import java.util.ArrayList;
import java.util.List;

public class StrUtil {
    /**
     * 指定字符截取字符串
     */
    public static List<String> splitStr(String str, String key) {
        List<String> stringList = new ArrayList<>();
        String[] split = str.split(key);
        for (int i = 0; i < split.length; i++) {
            stringList.add(split[i]);
        }
        return stringList;
    }
}
