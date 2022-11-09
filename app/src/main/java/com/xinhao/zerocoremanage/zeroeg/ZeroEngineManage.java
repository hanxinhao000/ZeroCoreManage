package com.xinhao.zerocoremanage.zeroeg;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.xinhao.zerocoremanage.filebrowser.FileBrowserManage;
import com.xinhao.zerocoremanage.keybord.KeyBordManage;
import com.xinhao.zerocoremanage.keybord.KeyData;
import com.xinhao.zerocoremanage.keybord.TerminalView;
import com.xinhao.zerocoremanage.utils.LogUtils;
import com.xinhao.zerocoremanage.utils.UUtils;
import com.xinhao.zerocoremanage.vshell.Config;
import com.xinhao.zerocoremanage.vshell.Installer;
import com.xinhao.zerocoremanage.vshell.VShellManage;

import java.util.ArrayList;

public class ZeroEngineManage {
    public static String TAG = "ZeroEngineManage";
    public static String ZERO_TERMUX_PACKAGE = "com.termux.zerocore.zero.engine.ZeroCoreManage";
    public static final int INSTALLING = 10002;
    public static final int INSTALL_COMPLETE = 10003;

    public static ArrayList<String> getEnvironment() {
        LogUtils.d(TAG, "getEnvironment");
        ArrayList<String> environment = VShellManage.getEnvironment();
        return environment;
    }

    public static ArrayList<String> getProcessArgs() {
        LogUtils.d(TAG, "getProcessArgs");
        ArrayList<String> processArgs = VShellManage.getProcessArgs();
        return processArgs;
    }

    public static String getDataDirectory() {
        LogUtils.d(TAG, "getDataDirectory");
        return Config.getDataDirectory(UUtils.getContext());
    }
    public static void setContext(Context mContext) {
        LogUtils.d(TAG, "setContext");
        UUtils.setContext(mContext);
    }
    public static void setEngineContext(Context mEngineContext) {
        LogUtils.d(TAG, "setEngineContext");
        UUtils.setEngineContext(mEngineContext);
    }
    public static void install(Handler mHandler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Installer.setupIfNeeded(UUtils.getContext(), UUtils.getEngineContext(), mHandler);
            }
        }).start();
    }
    public static String getVersionName(Context mContext) {
        LogUtils.d(TAG, "getVersionName");
        return UUtils.getVersionName(mContext);
    }

    public static void setRunHandler(Handler mHandler) {
        LogUtils.d(TAG, "setHandler");
        UUtils.setHandler(mHandler);
    }
    public static void setKeyHandler(Handler mHandler) {
        LogUtils.d(TAG, "setKeyHandler");
        KeyBordManage.setKeyHandler(mHandler);
    }

    public static View getKeyView() {
        LogUtils.d(TAG, "getKeyView");
        return KeyBordManage.getKeyView();
    }

    public static void initKeyView() {
        KeyBordManage.init();
        LogUtils.d(TAG, "initKeyView");
    }

    public static void installFileBrowser(Context mContext, Context mEngineContext, Handler mInstallHandler) {
        LogUtils.d(TAG, "installFileBrowser");
        FileBrowserManage.install(mContext, mEngineContext, mInstallHandler);
    }
}
