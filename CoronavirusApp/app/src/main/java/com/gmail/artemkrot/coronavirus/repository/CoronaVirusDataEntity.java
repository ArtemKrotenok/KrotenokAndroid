package com.gmail.artemkrot.coronavirus.repository;

import java.util.List;

public class CoronaVirusDataEntity {
    private String lastCheckTimeText;
    private String totalConfirmed;
    private String totalDeaths;
    private String totalRecovered;
    private List<InfectionPointEntity> infectionPointList;
    private List<InfectionCountry> infectionCountryList;

    public String getLastCheckTimeText() {
        return lastCheckTimeText;
    }

    public void setLastCheckTimeText(String lastCheckTimeText) {
        this.lastCheckTimeText = lastCheckTimeText;
    }

    public String getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(String totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public List<InfectionPointEntity> getInfectionPointList() {
        return infectionPointList;
    }

    public void setInfectionPointList(List<InfectionPointEntity> infectionPointList) {
        this.infectionPointList = infectionPointList;
    }

    public List<InfectionCountry> getInfectionCountryList() {
        return infectionCountryList;
    }

    public void setInfectionCountryList(List<InfectionCountry> infectionCountryList) {
        this.infectionCountryList = infectionCountryList;
    }
}