package com.gmail.artemkrot.jokeabout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class JokeAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(JokeStartWorker.class)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance().enqueue(myWorkRequest);
    }
}