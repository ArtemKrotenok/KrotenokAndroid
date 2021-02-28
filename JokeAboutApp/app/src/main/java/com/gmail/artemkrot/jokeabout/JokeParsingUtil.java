package com.gmail.artemkrot.jokeabout;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class JokeParsingUtil {

    private static final String TAG = JokeParsingUtil.class.getSimpleName();
    public static final String TAG_VALUE = "value";
    public static final String TAG_JOKE = "joke";

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