package com.xinhao.zerocoremanage.zeroeg;

import com.xinhao.zerocoremanage.utils.LogUtils;

import java.util.ArrayList;

public class ZeroEngineManage {
    public static String TAG = "ZeroEngineManage";
    public static String ZERO_TERMUX_PACKAGE = "com.termux.zerocore.zero.engine.ZeroCoreManage";

    public static ArrayList<String> getQemuStartStrings() {
        LogUtils.d(TAG, "getQemuStartStrings");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1111");
        arrayList.add("1111");
        arrayList.add("1111");
        arrayList.add("1111");
        arrayList.add("1111");
        arrayList.add("1111");
        arrayList.add("1111");
        return arrayList;
    }
}
