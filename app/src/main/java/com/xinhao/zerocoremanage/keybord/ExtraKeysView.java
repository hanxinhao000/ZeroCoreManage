package com.xinhao.zerocoremanage.keybord;

import android.os.Message;

public class ExtraKeysView {
    public static void sendKey2(TerminalView mTerminalView, String msg) {
        Message message = new Message();
        message.what = KeyBordManage.KEY_OTHER;
        message.obj = msg;
        KeyBordManage.mHandlerA.sendMessage(message);
    }
}
