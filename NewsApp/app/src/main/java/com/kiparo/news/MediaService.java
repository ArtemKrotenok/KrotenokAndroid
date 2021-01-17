package com.kiparo.news;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MediaService {
    private static final String TAG = MediaService.class.getSimpleName();

    public static MediaEntity getMediaFromJSON(JSONObject jsonObject) {
        MediaEntity mediaEntity = new MediaEntity();
        try {
            mediaEntity.setUrl(jsonObject.getString("url"));
            mediaEntity.setFormat(jsonObject.getString("format"));
            mediaEntity.setHeight(jsonObject.getInt("height"));
            mediaEntity.setWidth(jsonObject.getInt("width"));
            mediaEntity.setType(jsonObject.getString("type"));
            mediaEntity.setSubType(jsonObject.getString("subtype"));
            mediaEntity.setCaption(jsonObject.getString("caption"));
            mediaEntity.setCopyright(jsonObject.getString("copyright"));
        } catch (JSONException exception) {
            Log.e(TAG, exception.getMessage());
        }
        return mediaEntity;
    }
}