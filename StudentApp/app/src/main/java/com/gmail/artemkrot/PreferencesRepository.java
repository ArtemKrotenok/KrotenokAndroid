package com.gmail.artemkrot;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesRepository {
    private static final String SEARCH_FILTER = "SEARCH_FILTER";
    private static final String PREFERENCES_NAME = "PREFERENCES_NAME";

    private final SharedPreferences sharedPreferences;

    public PreferencesRepository(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public String getSearchValue() {
        return sharedPreferences.getString(SEARCH_FILTER, "");
    }

    public void saveSearchValue(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SEARCH_FILTER, value);
        editor.apply();
    }
}