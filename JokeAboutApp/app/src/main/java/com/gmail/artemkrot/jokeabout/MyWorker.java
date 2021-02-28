package com.gmail.artemkrot.jokeabout;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.gmail.artemkrot.jokeabout.App.CHANNEL_ID;
import static com.gmail.artemkrot.jokeabout.App.NOTIFY_ID;

public class MyWorker extends Worker implements JokeReadyListener {

    private final Context context;
    private final PreferencesRepository preferencesRepository;
    private JokeService jokeService;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        preferencesRepository = new PreferencesRepository(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        startDownloadJoke();
        return Result.success();
    }

    @Override
    public void onJokeReady() {
        String textJoke = jokeService.getTextJoke();
        showJoke(textJoke);
    }

    private void startDownloadJoke() {
        String fistName = preferencesRepository.getFistNameValue();
        String lastName = preferencesRepository.getLastNameValue();
        JokeReadyListener listener = this;
        jokeService = new JokeService(fistName, lastName, listener);
        jokeService.getJoke();
    }

    private void showJoke(String textJoke) {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(context.getString(R.string.title_notification))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(textJoke))
                        .setContentIntent(contentIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }
}