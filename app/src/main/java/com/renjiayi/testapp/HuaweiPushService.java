package com.renjiayi.testapp;

import android.text.TextUtils;
import android.util.Log;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

public class HuaweiPushService extends HmsMessageService {
    @Override
    public void onNewToken(String s) {
        Log.d("renjiayi", "Refreshed token: " + s);
        // send the token to your app server.
        if (!TextUtils.isEmpty(s)) {
            Log.d("renjiayi", "onNewToken: " + s);
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("renjiayi", "onMessageReceived: " + remoteMessage);
        // TODO: your's other processing logic
    }
}
