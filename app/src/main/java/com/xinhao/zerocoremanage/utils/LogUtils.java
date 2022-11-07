package com.xinhao.zerocoremanage.utils;

import android.util.Log;

public class LogUtils {
    public static String TAG = "ZeroEngine";

    public static void e(String tag, String msg) {
        Log.e(TAG + "--" + tag, msg);
    }
    public static void d(String tag, String msg) {
        Log.d(TAG + "--" + tag, msg);
    }

}
