package com.gmail.artemkrot.coronavirus.net;

import android.util.Log;

import com.gmail.artemkrot.coronavirus.repository.CoronaVirusDataEntity;
import com.gmail.artemkrot.coronavirus.repository.InfectionCountry;
import com.gmail.artemkrot.coronavirus.repository.InfectionPointEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataParsingUtil {

    private static final String TAG = DataParsingUtil.class.getSimpleName();

    public static final String LAST_CHECK_TIME_TEXT = "lastCheckTimeText";
    public static final String CHINA = "china";
    public static final String TOTAL_CONFIRMED = "totalConfirmed";
    public static final String TOTAL_CONFIRMED1 = "totalConfirmed";
    public static final String TOTAL_DEATHS = "totalDeaths";
    public static final String TOTAL_RECOVERED = "totalRecovered";
    public static final String DATA = "data";
    public static final String COUNTRY = "country";
    public static final String LAT = "lat";
    public static final String LONG = "long";
    public static final String CONFIRMED = "confirmed";

    public static final String FAIL_TO_PARSE_JSON_STRING = "fail to parse json string";

    public static CoronaVirusDataEntity getCoronaVirusDataFromJSONS(String downloadData) {
        try {
            JSONObject rootJSONObject = new JSONObject(downloadData);
            CoronaVirusDataEntity coronaVirusData = new CoronaVirusDataEntity();
            coronaVirusData.setLastCheckTimeText(rootJSONObject.getString(LAST_CHECK_TIME_TEXT));
            JSONObject jsonObject = rootJSONObject.getJSONObject(CHINA);
            coronaVirusData.setTotalConfirmed(jsonObject.getLong(TOTAL_CONFIRMED));
            coronaVirusData.setTotalConfirmed(jsonObject.getLong(TOTAL_CONFIRMED1));
            coronaVirusData.setTotalDeaths(jsonObject.getLong(TOTAL_DEATHS));
            coronaVirusData.setTotalRecovered(jsonObject.getLong(TOTAL_RECOVERED));
            JSONArray infectionPointListJSON = jsonObject.getJSONArray(DATA);
            coronaVirusData.setInfectionPointList(getInfectionPointListFromJSON(infectionPointListJSON));
            coronaVirusData.setInfectionCountryList(calculateConfirmedInCountry(coronaVirusData.getInfectionPointList()));
            return coronaVirusData;
        } catch (JSONException e) {
            Log.e(TAG, FAIL_TO_PARSE_JSON_STRING);
        }
        return null;
    }

    public static InfectionPointEntity getInfectionPointFromJSON(JSONObject jsonObject) {
        InfectionPointEntity countryData = new InfectionPointEntity();
        try {
            countryData.setName(jsonObject.getString(COUNTRY));
            countryData.setLat(jsonObject.getDouble(LAT));
            countryData.setLng(jsonObject.getDouble(LONG));
            countryData.setConfirmed(jsonObject.getLong(CONFIRMED));
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
            Log.e(TAG, FAIL_TO_PARSE_JSON_STRING);
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

    private static List<InfectionCountry> calculateConfirmedInCountry(List<InfectionPointEntity> infectionPointList) {
        Map<String, Long> infectionCountryMap = getMapFromPointsList(infectionPointList);
        List<InfectionCountry> infectionCountryList = getListFromMap(infectionCountryMap);
        return sortByConfirmed(infectionCountryList);
    }

    private static Map<String, Long> getMapFromPointsList(List<InfectionPointEntity> infectionPointList) {
        Map<String, Long> infectionCountryMap = new HashMap<>();
        for (InfectionPointEntity infectionPoint : infectionPointList) {
            String nameCountry = infectionPoint.getName();
            Long confirmed = infectionPoint.getConfirmed();
            Long totalConfirmed = infectionCountryMap.get(nameCountry);
            infectionCountryMap.put(nameCountry, totalConfirmed == null ? confirmed : totalConfirmed + confirmed);
        }
        return infectionCountryMap;
    }

    private static List<InfectionCountry> getListFromMap(Map<String, Long> infectionCountryMap) {
        List<InfectionCountry> infectionCountryList = new ArrayList<>();
        for (Map.Entry entry : infectionCountryMap.entrySet()) {
            InfectionCountry infectionCountry = new InfectionCountry();
            infectionCountry.setName(entry.getKey().toString());
            infectionCountry.setConfirmed((Long) entry.getValue());
            infectionCountryList.add(infectionCountry);
        }
        return infectionCountryList;
    }

    private static List<InfectionCountry> sortByConfirmed(List<InfectionCountry> infectionCountryList) {
        infectionCountryList.sort(new Comparator<InfectionCountry>() {
            @Override
            public int compare(InfectionCountry infectionCountry1, InfectionCountry infectionCountry2) {
                long confirmed1 = infectionCountry1.getConfirmed();
                long confirmed2 = infectionCountry2.getConfirmed();
                return Long.compare(confirmed2, confirmed1);
            }
        });
        return infectionCountryList;
    }
}