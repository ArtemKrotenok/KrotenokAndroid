package com.kiparo.news.util;

import android.util.Log;

import com.kiparo.news.repository.MediaEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MediaUtil {
    private static final String TAG = MediaUtil.class.getSimpleName();

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

    public static MediaEntity getMediaByFormat(List<MediaEntity> mediaEntityList, Object formatMedia) {
        for (MediaEntity mediaEntity : mediaEntityList) {
            if (mediaEntity.getFormat().equals(formatMedia)) {
                return mediaEntity;
            }
        }
        return null;
    }
}