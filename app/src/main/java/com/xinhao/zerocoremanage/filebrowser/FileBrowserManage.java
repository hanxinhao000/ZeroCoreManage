package com.xinhao.zerocoremanage.filebrowser;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;

import com.xinhao.zerocoremanage.utils.LogUtils;
import com.xinhao.zerocoremanage.utils.UUtils;
import com.xinhao.zerocoremanage.vshell.Config;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

public class FileBrowserManage {

    public static final String TAG = "FileBrowserManage";
    public static final String PATH_AARCH64 = "linux-arm64.tar.gz";
    public static final String PATH_ARM = "linux-armv7.tar.gz";
    public static final String PATH_X86_64 = "linux-amd64.tar.gz";
    public static final String PATH_X86 = "linux-386.tar.gz";

    public static final String SH_AARCH64 = "cd ~ && cd ~ && cd .filebrowser  && chmod 777 filebrowser_arm64.sh && ./filebrowser_arm64.sh \n";
    public static final String SH_ARM = "cd ~ && cd ~ && cd .filebrowser &&  chmod 777 filebrowser_arm.sh && ./filebrowser_arm.sh \n";
    public static final String SH_X86_64 = "cd ~ && cd ~ && cd .filebrowser &&  chmod 777 filebrowser_amd.sh && ./filebrowser_amd.sh \n";
    public static final String SH_X86 = "cd ~ && cd ~ && cd .filebrowser &&  chmod 777 filebrowser_386.sh && ./filebrowser_386.sh \n";

    public static final String ZERO_ASSETS_NAME_AARCH64 = "zipcommand/filebrowser_arm64.sh";
    public static final String ZERO_ASSETS_NAME_ARM = "zipcommand/filebrowser_arm.sh";
    public static final String ZERO_ASSETS_NAME_X86_64 = "zipcommand/filebrowser_amd.sh";
    public static final String ZERO_ASSETS_NAME_X86 = "zipcommand/filebrowser_386.sh";

    public static String FILE_BROWSER_PATH = "";
    public static String FILE_BROWSER_ASSETS_PATH = "";

    public static final int FILE_BROWSER_INSTALLING = 10002;
    public static final int FILE_BROWSER_COMPLETE = 10003;

    public static void install(Context mContext, Context mEngineContext, Handler mInstallHandler) {
        FILE_BROWSER_PATH = Config.getDataDirectory(mContext) + "/home/.filebrowser/";
        mInstallHandler.sendEmptyMessage(FILE_BROWSER_INSTALLING);

        try {
            String s = UUtils.determineTermuxArchName();
            String fileName = "";
            String shFileName = "";
            String shCommend = "";
            AssetManager mEngineAssets = mEngineContext.getAssets();
            AssetManager mZeroAssets = mContext.getAssets();
            InputStream mEngineInputStream = null;
            InputStream mZeroInputStream = null;
            LogUtils.d(TAG, "assets list:" + Arrays.toString(mEngineAssets.list("")));
            switch (s) {
                case "aarch64":
                    fileName = PATH_AARCH64;
                    shCommend = SH_AARCH64;
                    shFileName = FILE_BROWSER_PATH + "filebrowser_arm64.sh";
                    LogUtils.d(TAG, "install aarch64");
                    mEngineInputStream = mEngineAssets.open("filebrowser/linux-arm64.tz");
                    mZeroInputStream = mZeroAssets.open(ZERO_ASSETS_NAME_AARCH64);
                    break;
                case "arm":
                    fileName = PATH_ARM;
                    shCommend = SH_ARM;
                    shFileName = FILE_BROWSER_PATH + "filebrowser_arm.sh";
                    LogUtils.d(TAG, "install arm");
                    mEngineInputStream = mEngineAssets.open("filebrowser/linux-armv7.tz");
                    mZeroInputStream = mZeroAssets.open(ZERO_ASSETS_NAME_ARM);
                    break;
                case "x86_64":
                    fileName = PATH_X86_64;
                    shCommend = SH_X86_64;
                    shFileName = FILE_BROWSER_PATH + "filebrowser_amd.sh";
                    LogUtils.d(TAG, "install x86_64");
                    mEngineInputStream = mEngineAssets.open("filebrowser/linux-amd64.tz");
                    mZeroInputStream = mZeroAssets.open(ZERO_ASSETS_NAME_X86_64);
                    break;
                case "i686":
                    fileName = PATH_X86;
                    shCommend = SH_X86;
                    shFileName = FILE_BROWSER_PATH + "filebrowser_386.sh";
                    LogUtils.d(TAG, "install x86");
                    mEngineInputStream = mEngineAssets.open("filebrowser/linux-386.tz");
                    mZeroInputStream = mZeroAssets.open(ZERO_ASSETS_NAME_X86);
                    break;
            }
            File outputFile = new File(FILE_BROWSER_PATH, fileName);
            File shOutputFile = new File(shFileName);
            if (!outputFile.exists()) {
                LogUtils.d(TAG, "writerFileRawInput file path: " + outputFile.getAbsolutePath());
                UUtils.writerFileRawInput(outputFile, mEngineInputStream);
                UUtils.writerFileRawInput(shOutputFile, mZeroInputStream);
            } else {
                LogUtils.d(TAG, "writerFileRawInput file path exists: " + outputFile.getAbsolutePath());
            }
            Message message = new Message();
            message.obj = shCommend;
            message.what = FILE_BROWSER_COMPLETE;
            mInstallHandler.sendMessage(message);
        } catch (Exception e) {
            LogUtils.d(TAG, "install error: " + e);
        }

    }
}
