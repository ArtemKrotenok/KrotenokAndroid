package com.gmail.artemkrot.jokeabout;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesRepository {

    private static final String FIST_NAME_VALUE = "FIST_NAME_VALUE";
    private static final String LAST_NAME_VALUE = "LAST_NAME_VALUE";
    private static final String TIMER_VALUE = "TIMER_VALUE";
    private static final String PREFERENCES_NAME = "PREFERENCES_NAME";
    private static final long DEFAULT_VALUE = 1000L;

    private final SharedPreferences sharedPreferences;

    public PreferencesRepository(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public long getTimerValue() {
        return sharedPreferences.getLong(TIMER_VALUE, DEFAULT_VALUE);
    }

    public void saveTimerValue(long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(TIMER_VALUE, value);
        editor.apply();
    }

    public String getFistNameValue() {
        return sharedPreferences.getString(FIST_NAME_VALUE, "");
    }

    public void saveFistNameValue(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIST_NAME_VALUE, value);
        editor.apply();
    }

    public String getLastNameValue() {
        return sharedPreferences.getString(LAST_NAME_VALUE, "");
    }

    public void saveLastNameValue(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_NAME_VALUE, value);
        editor.apply();
    }
}