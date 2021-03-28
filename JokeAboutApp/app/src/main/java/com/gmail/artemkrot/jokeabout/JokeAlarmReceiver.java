package com.gmail.artemkrot.jokeabout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class JokeAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, JokeService.class));
        JokeUtil.setAlarmForShow(context);
    }
}