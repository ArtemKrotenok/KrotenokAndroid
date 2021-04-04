package com.gmail.artemkrot.jokeabout;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JokeService extends Service {

    private static final String URL_ADDRESS = "https://api.icndb.com/jokes/random";
    private static final String PARAM_NAME_FIRST_NAME = "firstName";
    private static final String PARAM_NAME_LAST_NAME = "lastName";
    private static final String TAG = JokeService.class.getSimpleName();

    private Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this;
        PreferencesRepository preferencesRepository = new PreferencesRepository(context);
        String fistName = preferencesRepository.getFistNameValue();
        String lastName = preferencesRepository.getLastNameValue();
        downloadAndShowJoke(fistName, lastName);
        return super.onStartCommand(intent, flags, startId);
    }

    private void downloadAndShowJoke(String fistName, String lastName) {
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
                String messageNotification = JokeUtil.getTextJokeFromData(responseData);
                String titleNotification = context.getString(R.string.title_notification);
                NotificationUtil.showNotification(context, titleNotification, messageNotification);
            }
        });
    }
}