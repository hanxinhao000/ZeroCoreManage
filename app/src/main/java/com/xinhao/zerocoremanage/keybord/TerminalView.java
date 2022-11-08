package com.xinhao.zerocoremanage.keybord;

import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

public class TerminalView {

    public static void sendTextToTerminal(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            if ("CTRL".equals(msg) || "ctrl".equals(msg)) {
                return;
            }
            Message message = new Message();
            message.what = KeyBordManage.KEY_DEF;
            message.obj = msg;
            KeyBordManage.mHandlerA.sendMessage(message);
        }
    }

    public static void sendTextToTerminalAlt(String msg, boolean alt) {
        Message message = new Message();
        message.what = KeyBordManage.KEY_ALT;
        message.obj = msg;
        KeyBordManage.mHandlerA.sendMessage(message);
    }
    public static void sendTextToTerminalCtrl(String msg, boolean ctrl) {
        Message message = new Message();
        message.what = KeyBordManage.KEY_CTRL;
        message.obj = msg;
        KeyBordManage.mHandlerA.sendMessage(message);
    }

}
