package com.example.psq.utils;

import com.example.psq.base.BaseConstant;

public class SpData {
    //api地址
    public static String getBaseUrl(){
        return SpUtil.getInstance().getString(BaseConstant.API_ADDRESS);
    }
}
