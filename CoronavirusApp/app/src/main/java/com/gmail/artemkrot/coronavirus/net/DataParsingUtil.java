package com.gmail.artemkrot.coronavirus.net;

import android.icu.text.DecimalFormat;
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

    public static CoronaVirusDataEntity getCoronaVirusDataFromJSONS(String downloadData) {
        try {
            JSONObject rootJSONObject = new JSONObject(downloadData);
            CoronaVirusDataEntity coronaVirusData = new CoronaVirusDataEntity();
            coronaVirusData.setLastCheckTimeText(rootJSONObject.getString("lastCheckTimeText"));
            JSONObject jsonObject = rootJSONObject.getJSONObject("china");
            coronaVirusData.setTotalConfirmed(jsonObject.getString("totalConfirmed"));
            coronaVirusData.setTotalConfirmed(formattedNumber(jsonObject.getLong("totalConfirmed")));
            coronaVirusData.setTotalDeaths(formattedNumber(jsonObject.getLong("totalDeaths")));
            coronaVirusData.setTotalRecovered(formattedNumber(jsonObject.getLong("totalRecovered")));
            JSONArray infectionPointListJSON = jsonObject.getJSONArray("data");
            coronaVirusData.setInfectionPointList(getInfectionPointListFromJSON(infectionPointListJSON));
            coronaVirusData.setInfectionCountryList(calculateConfirmedInCountry(coronaVirusData.getInfectionPointList()));
            return coronaVirusData;
        } catch (JSONException e) {
            Log.e(TAG, "fail to parse json string");
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
            infectionCountry.setConfirmed(formattedNumber((Long) entry.getValue()));
            infectionCountryList.add(infectionCountry);
        }
        return infectionCountryList;
    }

    private static List<InfectionCountry> sortByConfirmed(List<InfectionCountry> infectionCountryList) {
        infectionCountryList.sort(new Comparator<InfectionCountry>() {
            @Override
            public int compare(InfectionCountry infectionCountry1, InfectionCountry infectionCountry2) {
                String confirmed1 = infectionCountry1.getConfirmed().replaceAll("\\D", "");
                String confirmed2 = infectionCountry2.getConfirmed().replaceAll("\\D", "");
                return Long.valueOf(confirmed2).compareTo(Long.valueOf(confirmed1));
            }
        });
        return infectionCountryList;
    }

    private static String formattedNumber(Long number) {
        return new DecimalFormat("###,###").format(number);
    }
}