package com.kiparo.news;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsUtil {
    private static final String TAG = NewsUtil.class.getSimpleName();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private List<NewsEntity> newsItemList = new ArrayList<>();

    public static NewsEntity getNewsFromJSON(JSONObject jsonObject) {
        NewsEntity newsEntity = new NewsEntity();
        try {
            newsEntity.setTitle(jsonObject.getString("title"));
            newsEntity.setSummary(jsonObject.getString("abstract"));
            newsEntity.setStoryURL(jsonObject.getString("url"));
            newsEntity.setByline(jsonObject.getString("byline"));
            newsEntity.setPublishedDate(jsonObject.getString("published_date"));
            List<MediaEntity> mediaEntityList = new ArrayList<>();
            JSONArray mediaArray = jsonObject.getJSONArray("multimedia");
            for (int i = 0; i < mediaArray.length(); i++) {
                JSONObject mediaObject = mediaArray.getJSONObject(i);
                MediaEntity mediaEntity = MediaUtil.getMediaFromJSON(mediaObject);
                mediaEntityList.add(mediaEntity);
            }
            newsEntity.setMediaEntityList(mediaEntityList);
        } catch (JSONException exception) {
            Log.e(TAG, exception.getMessage());
        }
        return newsEntity;
    }
}