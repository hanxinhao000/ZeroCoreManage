package com.xinhao.zerocoremanage.keybord;

import android.os.Handler;
import android.view.View;

public class KeyBordManage {

    public static final int KEY_DEF = 60000;
    public static final int KEY_ALT = 60001;
    public static final int KEY_CTRL = 60002;
    public static final int KEY_OTHER = 60003;

    public static KeyData keyData;
    public static Handler mHandlerA;
    public static void init() {
        keyData = new KeyData();
        keyData.init();
    }

    public static View getKeyView() {
        return KeyData.mView;
    }

    public static void setKeyHandler(Handler mHandler) {
        mHandlerA = mHandler;
    }

}
