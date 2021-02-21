package com.gmail.artemkrot.taxi.net;

import android.util.Log;

import com.gmail.artemkrot.taxi.repository.CarLocationEntity;
import com.google.android.gms.maps.model.LatLng;

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

    public static List<CarLocationEntity> getCarLocationsListFromJSONS(String downloadData) {
        try {
            JSONObject rootJSONObject = new JSONObject(downloadData);
            JSONArray carLocationsListListJSON = rootJSONObject.getJSONArray("poiList");
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
            Log.e(TAG, "fail to parse json");
        }
        return null;
    }

    private static CarLocationEntity getCarLocation(JSONObject carLocationJSON) {
        try {
            CarLocationEntity carLocation = new CarLocationEntity();
            carLocation.setId(carLocationJSON.getLong("id"));
            carLocation.setFleetType(carLocationJSON.getString("fleetType"));
            carLocation.setHeading(carLocationJSON.getString("heading"));
            JSONObject locationJSON = carLocationJSON.getJSONObject("coordinate");
            double latitude = locationJSON.getDouble("latitude");
            double longitude = locationJSON.getDouble("longitude");
            carLocation.setCoordinate(new LatLng(latitude, longitude));
            return carLocation;
        } catch (JSONException e) {
            Log.e(TAG, "fail to parse json");
        }
        return null;
    }

    public static InfectionPointEntity getInfectionPointFromJSON(JSONObject jsonObject) {
        InfectionPointEntity countryData = new InfectionPointEntity();
        try {
            countryData.setName(jsonObject.getString("country"));
            countryData.setNavLat(jsonObject.getDouble("lat"));
            countryData.setNavLong(jsonObject.getDouble("long"));
            countryData.setConfirmed(jsonObject.getLong("confirmed"));
        } catch (JSONException exception) {
            Log.e(TAG, exception.getMessage());
        }
        return countryData;
    }

    private static List<InfectionPointEntity> getInfectionPointListFromJSON(JSONArray infectionPointListJSON) {
        List<InfectionPointEntity> infectionPointEntityList = new ArrayList<>();
        try {
            for (int i = 0; i < infectionPointListJSON.length(); i++) {
                JSONObject infectionPointJSON = infectionPointListJSON.getJSONObject(i);
                InfectionPointEntity infectionPoint = DataParsingUtil.getInfectionPointFromJSON(infectionPointJSON);
                infectionPointEntityList.add(infectionPoint);
            }
            return infectionPointEntityList;
        } catch (JSONException e) {
            Log.e(TAG, "fail to parse json string");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}