package com.gmail.artemkrot.coronavirus.net;

import android.os.Handler;
import android.os.Looper;

import com.gmail.artemkrot.coronavirus.repository.CoronaVirusDataEntity;
import com.gmail.artemkrot.coronavirus.repository.CoronaVirusDataRepository;

import java.net.HttpURLConnection;
import java.net.URL;

import static com.gmail.artemkrot.coronavirus.net.Constant.URL_DATA_REQUEST_ADDRESS;
import static com.gmail.artemkrot.coronavirus.net.Constant.URL_DATA_REQUEST_HEADER_KEY_VALUE;
import static com.gmail.artemkrot.coronavirus.net.Constant.URL_DATA_REQUEST_HEADER_NAME_KEY;

public class DataLoader implements Callback {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private CoronaVirusDataEntity coronaVirusData = new CoronaVirusDataEntity();
    private final DataLoadListener dataLoadListener;
    private Runnable load;

    public DataLoader(DataLoadListener dataLoadListener) {
        this.dataLoadListener = dataLoadListener;
    }

    @Override
    public void onResult(final String downloadData) {
        load = new Runnable() {
            @Override
            public void run() {
                coronaVirusData = DataParsingUtil.getCoronaVirusDataFromJSONS(downloadData);
                CoronaVirusDataRepository coronaVirusDataRepository = CoronaVirusDataRepository.getInstance();
                coronaVirusDataRepository.setCoronaVirusData(coronaVirusData);
                dataLoadListener.onFinishLoad();
            }
        };
        handler.postDelayed(load, 0);
    }

    public void finish() {
        if (load != null) {
            handler.removeCallbacks(load);
        }
    }

    public void loadData() {
        loadResource(this);
    }

    public CoronaVirusDataEntity getData() {
        return coronaVirusData;
    }

    private void loadResource(final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(URL_DATA_REQUEST_ADDRESS);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty(URL_DATA_REQUEST_HEADER_NAME_KEY, URL_DATA_REQUEST_HEADER_KEY_VALUE);
                    String readStream = DataParsingUtil.readStream(con.getInputStream());
                    callback.onResult(readStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}