package com.gmail.artemkrot.taxi.net;

import android.util.Log;

import com.gmail.artemkrot.taxi.repository.CarLocationEntity;
import com.yandex.mapkit.geometry.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataParsingUtil {

    private static final String TAG = DataParsingUtil.class.getSimpleName();

    public static final String FAIL_DOWNLAND_DATA = "fail downland data ";
    public static final String FAIL_TO_PARSE_JSON = "fail to parse json";

    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String COORDINATE = "coordinate";
    public static final String HEADING = "heading";
    public static final String FLEET_TYPE = "fleetType";
    public static final String ID = "id";
    public static final String POI_LIST = "poiList";

    public static List<CarLocationEntity> getCarLocationsListFromJSONS(String downloadData) {
        try {
            JSONObject rootJSONObject = new JSONObject(downloadData);
            JSONArray carLocationsListListJSON = rootJSONObject.getJSONArray(POI_LIST);
            List<CarLocationEntity> carLocationsList = new ArrayList<>();
            for (int i = 0; i < carLocationsListListJSON.length(); i++) {
                JSONObject carLocationJSON = carLocationsListListJSON.getJSONObject(i);
                CarLocationEntity carLocation = getCarLocation(carLocationJSON);
                if (carLocation != null) {
                    carLocationsList.add(carLocation);
                }
            }
            return carLocationsList;
        } catch (JSONException e) {
            Log.e(TAG, FAIL_TO_PARSE_JSON);
        }
        return null;
    }

    private static CarLocationEntity getCarLocation(JSONObject carLocationJSON) {
        try {
            CarLocationEntity carLocation = new CarLocationEntity();
            carLocation.setId(carLocationJSON.getLong(ID));
            carLocation.setFleetType(carLocationJSON.getString(FLEET_TYPE));
            carLocation.setHeading(carLocationJSON.getString(HEADING));
            JSONObject locationJSON = carLocationJSON.getJSONObject(COORDINATE);
            double latitude = locationJSON.getDouble(LATITUDE);
            double longitude = locationJSON.getDouble(LONGITUDE);
            carLocation.setCoordinate(new Point(latitude, longitude));
            return carLocation;
        } catch (JSONException e) {
            Log.e(TAG, FAIL_TO_PARSE_JSON);
        }
        return null;
    }

    public static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException exception) {
            Log.e(TAG, FAIL_DOWNLAND_DATA + exception.getMessage());
        }
        return sb.toString();
    }
}