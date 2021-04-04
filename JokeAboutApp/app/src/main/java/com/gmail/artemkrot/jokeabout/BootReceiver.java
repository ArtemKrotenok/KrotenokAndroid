package com.gmail.artemkrot.jokeabout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            JokeUtil.setAlarmForShow(context);
        }
    }
}