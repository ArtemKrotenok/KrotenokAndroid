package com.kiparo.news.servise;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.kiparo.news.activity.NewsListAdapter;
import com.kiparo.news.repository.NewsEntity;
import com.kiparo.news.util.NewsUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsLoader implements Callback {

    private static final String TAG = NewsLoader.class.getSimpleName();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final List<NewsEntity> newsItemList = new ArrayList<>();
    private final String urlDataRequest;
    private final NewsLoadListener newsLoadListener;

    public NewsLoader(String urlDataRequest, NewsLoadListener newsLoadListener) {
        this.urlDataRequest = urlDataRequest;
        this.newsLoadListener = newsLoadListener;
    }

    @Override
    public void onResult(final String data) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject;

                try {
                    jsonObject = new JSONObject(data);
                    JSONArray resultArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject newsObject = resultArray.getJSONObject(i);
                        NewsEntity newsEntity = NewsUtil.getNewsFromJSON(newsObject);
                        newsItemList.add(newsEntity);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "fail to parse json string");
                }
                newsLoadListener.onFinishLoad();
            }
        }, 0);
    }

    public void loadNews() {
        loadResource(this, urlDataRequest);
    }

    public List<NewsEntity> getNews() {
        return newsItemList;
    }

    private static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void loadResource(final Callback callback, final String UrlDataRequest) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(UrlDataRequest);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    String readStream = readStream(con.getInputStream());
                    callback.onResult(readStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}