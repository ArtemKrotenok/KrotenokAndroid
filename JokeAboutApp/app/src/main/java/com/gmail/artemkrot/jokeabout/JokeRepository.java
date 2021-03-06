package com.gmail.artemkrot.jokeabout;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JokeRepository {

    private static final String TAG = JokeRepository.class.getSimpleName();
    private static final String URL_ADDRESS = "https://api.icndb.com/jokes/random";
    private static final String PARAM_NAME_FIRST_NAME = "firstName";
    private static final String PARAM_NAME_LAST_NAME = "lastName";
    private final String fistName;
    private final String lastName;
    private final JokeReadyListener jokeReadyListener;
    private String textJoke;

    public JokeRepository(String fistName, String lastName, JokeReadyListener jokeReadyListener) {
        this.fistName = fistName;
        this.lastName = lastName;
        this.jokeReadyListener = jokeReadyListener;
    }

    public void getJoke() {
        startDownloadData(fistName, lastName);
    }

    public String getTextJoke() {
        return textJoke;
    }

    private void startDownloadData(String fistName, String lastName) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL_ADDRESS).newBuilder();
        urlBuilder.addQueryParameter(PARAM_NAME_FIRST_NAME, fistName);
        urlBuilder.addQueryParameter(PARAM_NAME_LAST_NAME, lastName);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "fail to download data");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                textJoke = JokeParsingUtil.getTextJokeFromData(responseData);
                jokeReadyListener.onJokeReady();
            }
        });
    }
}