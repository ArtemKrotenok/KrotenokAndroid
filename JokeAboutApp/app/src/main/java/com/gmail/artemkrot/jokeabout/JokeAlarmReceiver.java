package com.gmail.artemkrot.jokeabout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class JokeAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(JokeStartServiceWorker.class).build();
        WorkManager.getInstance().enqueue(myWorkRequest);
    }
}