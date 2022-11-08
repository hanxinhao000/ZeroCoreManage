package com.xinhao.zerocoremanage.application;

import android.app.Application;
import android.os.Handler;

import com.xinhao.zerocoremanage.utils.LogUtils;
import com.xinhao.zerocoremanage.utils.UUtils;

public class ZeroEngineManageApplication extends Application {
    public static String TAG = "ZeroEngineManageApplication";
    private static Handler mHandler;
    private static ZeroEngineManageApplication mZeroEngineManageApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        mZeroEngineManageApplication = this;
        UUtils.initUUtils(mZeroEngineManageApplication, mHandler);
        LogUtils.d(TAG, "ZeroEngineManageApplication " + mZeroEngineManageApplication);
    }
}
