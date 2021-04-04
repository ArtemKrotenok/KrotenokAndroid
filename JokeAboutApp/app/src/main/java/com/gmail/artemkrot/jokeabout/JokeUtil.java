package com.gmail.artemkrot.jokeabout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timerValue, pendingIntent);
        }
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