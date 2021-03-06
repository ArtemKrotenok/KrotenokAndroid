package com.gmail.artemkrot.jokeabout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")) {
            SetAlarmUtil.setAlarm(context);
        }
    }
}