package com.kiparo.news.net;

import android.os.Handler;
import android.os.Looper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsLoader implements Callback {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final List<NewsEntity> newsItemList = new ArrayList<>();
    private final String urlDataRequest;
    private final NewsLoadListener newsLoadListener;
    private Runnable load;

    public NewsLoader(String urlDataRequest, NewsLoadListener newsLoadListener) {
        this.urlDataRequest = urlDataRequest;
        this.newsLoadListener = newsLoadListener;
    }

    @Override
    public void onResult(final String data) {
        load = new Runnable() {
            @Override
            public void run() {
                newsItemList.addAll(NewsParsingUtil.getNewsListFromJSONString(data));
                newsLoadListener.onFinishLoad();
            }
        };
        handler.postDelayed(load, 0);
    }

    public void finish() {
        if (load != null) {
            handler.removeCallbacks(load);
        }
    }

    public void loadNews() {
        loadResource(this, urlDataRequest);
    }

    public List<NewsEntity> getNews() {
        return newsItemList;
    }

    private void loadResource(final Callback callback, final String UrlDataRequest) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(UrlDataRequest);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    String readStream = NewsParsingUtil.readStream(con.getInputStream());
                    callback.onResult(readStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}