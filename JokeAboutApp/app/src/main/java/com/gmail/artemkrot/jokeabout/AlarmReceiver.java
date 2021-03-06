package com.gmail.artemkrot.jokeabout;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.gmail.artemkrot.jokeabout.NotificationUtil.CHANNEL_ID;
import static com.gmail.artemkrot.jokeabout.NotificationUtil.NOTIFY_ID;

public class AlarmReceiver extends BroadcastReceiver implements JokeReadyListener {
    private Context context;
    private PreferencesRepository preferencesRepository;
    private JokeRepository jokeRepository;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        preferencesRepository = new PreferencesRepository(context);
        startDownloadJoke();
    }

    @Override
    public void onJokeReady() {
        String textJoke = jokeRepository.getTextJoke();
        showJoke(textJoke);
    }

    private void startDownloadJoke() {
        String fistName = preferencesRepository.getFistNameValue();
        String lastName = preferencesRepository.getLastNameValue();
        JokeReadyListener listener = this;
        jokeRepository = new JokeRepository(fistName, lastName, listener);
        jokeRepository.getJoke();
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
        if (notificationManager.getNotificationChannel(String.valueOf(NOTIFY_ID)) == null) {
            NotificationUtil.createNotificationChannel(context);
        }
        notificationManager.notify(NOTIFY_ID, builder.build());
    }
}