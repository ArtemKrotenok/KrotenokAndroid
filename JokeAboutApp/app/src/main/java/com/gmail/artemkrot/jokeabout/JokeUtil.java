package com.gmail.artemkrot.jokeabout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONException;
import org.json.JSONObject;

import static com.gmail.artemkrot.jokeabout.NotificationUtil.CHANNEL_ID;
import static com.gmail.artemkrot.jokeabout.NotificationUtil.NOTIFY_ID;

public class JokeUtil {

    public static final String TAG_VALUE = "value";
    public static final String TAG_JOKE = "joke";
    private static final String TAG = JokeUtil.class.getSimpleName();

    public static void setAlarmForShow(Context context) {
        PreferencesRepository preferencesRepository = new PreferencesRepository(context);
        long timerValue = preferencesRepository.getTimerValue();
        if (timerValue != 0L) {
            Intent intent = new Intent(context, JokeAlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, timerValue, pendingIntent);
        }
    }

    public static void showJoke(Context context, String textJoke) {
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

    public static String getTextJokeFromData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject jsonJoke = jsonObject.getJSONObject(TAG_VALUE);
            return jsonJoke.getString(TAG_JOKE);
        } catch (JSONException e) {
            Log.e(TAG, "fail to parse json string");
        }
        return null;
    }
}