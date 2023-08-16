package com.example.psq.utils;

import android.widget.Toast;

import com.example.psq.base.BaseApplication;

public class TShow {

    public static void show(String msg) {
        BaseApplication.getHandler().post(() ->
                Toast.makeText(BaseApplication.context, msg, Toast.LENGTH_SHORT).show()
        );
    }

    public static void showLong(String msg) {
        BaseApplication.getHandler().post(() ->
                Toast.makeText(BaseApplication.context, msg, Toast.LENGTH_LONG).show()
        );
    }
}
