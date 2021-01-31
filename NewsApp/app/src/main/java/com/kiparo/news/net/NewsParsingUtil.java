package com.kiparo.news.net;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewsParsingUtil {

    private static final String FORMAT_MEDIA_SMALL_PICTURE = "Standard Thumbnail";
    private static final String FORMAT_MEDIA_BIG_PICTURE = "superJumbo";

    private static final String TAG = NewsParsingUtil.class.getSimpleName();

    public static NewsEntity getNewsFromJSON(JSONObject jsonObject) {
        NewsEntity newsEntity = new NewsEntity();
        try {
            newsEntity.setTitle(jsonObject.getString("title"));
            newsEntity.setSummary(jsonObject.getString("abstract"));
            newsEntity.setStoryURL(jsonObject.getString("url"));
            JSONArray mediaArray = jsonObject.getJSONArray("multimedia");
            for (int i = 0; i < mediaArray.length(); i++) {
                JSONObject mediaObject = mediaArray.getJSONObject(i);
                if (mediaObject.getString("format").equals(FORMAT_MEDIA_BIG_PICTURE)) {
                    newsEntity.setBigPictureURL(mediaObject.getString("url"));
                }
                if (mediaObject.getString("format").equals(FORMAT_MEDIA_SMALL_PICTURE)) {
                    newsEntity.setSmallPictureURL(mediaObject.getString("url"));
                }
            }
        } catch (JSONException exception) {
            Log.e(TAG, exception.getMessage());
        }
        return newsEntity;
    }

    public static List<NewsEntity> getNewsListFromJSONString(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            List<NewsEntity> newsItemList = new ArrayList<>();

            JSONArray resultArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject newsObject = resultArray.getJSONObject(i);
                NewsEntity newsEntity = NewsParsingUtil.getNewsFromJSON(newsObject);
                newsItemList.add(newsEntity);
            }
            return newsItemList;
        } catch (JSONException e) {
            Log.e(TAG, "fail to parse json string");
        }
        return Collections.emptyList();
    }

    public static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}