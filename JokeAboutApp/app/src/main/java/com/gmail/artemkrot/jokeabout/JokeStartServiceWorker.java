package com.gmail.artemkrot.jokeabout;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class JokeStartServiceWorker extends Worker {
    private Context context;

    public JokeStartServiceWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        context.startService(new Intent(context, JokeService.class));
        JokeUtil.setAlarmForShow(context);
        return null;
    }
}