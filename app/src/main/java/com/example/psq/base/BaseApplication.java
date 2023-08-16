package com.example.psq.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.example.psq.BuildConfig;
import com.example.psq.greendao.DaoManager;
import com.example.psq.greendao.dao.AnswerDAO;
import com.example.psq.greendao.dao.QBankDAO;
import com.example.psq.network.NetworkApi;
import com.example.psq.network.interceptor.NetworkRequiredInfo;
import com.example.psq.network.utils.LogUtil;

import java.util.List;

import me.jessyan.autosize.AutoSizeConfig;

public class BaseApplication extends Application {
    public static BaseApplication instance;
    public static Context context;

    private static Handler handler;
    public static List<QBankDAO> getQBanks;
    public static List<AnswerDAO> getAnswerDAOs;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        handler = new Handler();

        if (!BuildConfig.DEBUG) {
            LogUtil.init(false);
        }

        DaoManager.getInstance().getDaoMaster();
        DaoManager.getInstance().getDaoSession();

        NetworkApi.init(new NetworkRequiredInfo(this));
        AutoSizeConfig.getInstance().setUseDeviceSize(true).setExcludeFontScale(false);
    }

    public static Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }
}
