package com.gmail.artemkrot.jokeabout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class SetAlarmUtil {

    final static int RQS_TIME = 1;

    public static void setAlarm(Context context) {
        PreferencesRepository preferencesRepository = new PreferencesRepository(context);
        long timerValue = preferencesRepository.getTimerValue();
        cancelAlarm(context);
        if (timerValue == 0L) {
            cancelAlarm(context);
        } else setAlarm(timerValue, context);
    }

    private static void setAlarm(long timer, Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, RQS_TIME, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timer, timer,
                pendingIntent);
    }

    private static void cancelAlarm(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, RQS_TIME, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}