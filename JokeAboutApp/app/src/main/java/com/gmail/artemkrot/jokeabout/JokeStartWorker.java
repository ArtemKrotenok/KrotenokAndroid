package com.gmail.artemkrot.jokeabout;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JokeStartWorker extends Worker {

    private static final String URL_ADDRESS = "https://api.icndb.com/jokes/random";
    private static final String PARAM_NAME_FIRST_NAME = "firstName";
    private static final String PARAM_NAME_LAST_NAME = "lastName";
    private static final String TAG = JokeStartWorker.class.getSimpleName();

    private final Context context;

    public JokeStartWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        PreferencesRepository preferencesRepository = new PreferencesRepository(context);
        String fistName = preferencesRepository.getFistNameValue();
        String lastName = preferencesRepository.getLastNameValue();
        JokeUtil.setAlarmForShow(context);
        if (downloadAndShowJoke(fistName, lastName)) {
            return Result.success();
        } else {
            return Result.retry();
        }
    }

    private boolean downloadAndShowJoke(String fistName, String lastName) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL_ADDRESS).newBuilder();
        urlBuilder.addQueryParameter(PARAM_NAME_FIRST_NAME, fistName);
        urlBuilder.addQueryParameter(PARAM_NAME_LAST_NAME, lastName);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                String messageNotification = JokeUtil.getTextJokeFromData(responseData);
                String titleNotification = context.getString(R.string.title_notification);
                NotificationUtil.showNotification(context, titleNotification, messageNotification);
                return true;
            } else {
                Log.e(TAG, "fail to download data");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }
}