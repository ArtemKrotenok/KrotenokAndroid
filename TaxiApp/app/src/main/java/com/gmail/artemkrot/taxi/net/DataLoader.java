package com.gmail.artemkrot.taxi.net;

import android.os.Handler;
import android.os.Looper;

import com.gmail.artemkrot.taxi.repository.CarLocationEntity;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.artemkrot.taxi.net.Constant.URL_DATA_REQUEST_ADDRESS;

public class DataLoader implements Callback {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private List<CarLocationEntity> carLocationsList = new ArrayList<>();
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
                carLocationsList = DataParsingUtil.getCarLocationsListFromJSONS(downloadData);
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

    public List<CarLocationEntity> getData() {
        return carLocationsList;
    }

    private void loadResource(final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(URL_DATA_REQUEST_ADDRESS);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestProperty("Content-Type", "application/json");
                    String readStream = DataParsingUtil.readStream(con.getInputStream());
                    callback.onResult(readStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}