package com.gmail.artemkrot.coronavirus.repository;

import java.util.List;

public class CoronaVirusDataEntity {
    private String lastCheckTimeText;
    private long totalConfirmed;
    private long totalDeaths;
    private long totalRecovered;
    private List<InfectionPointEntity> infectionPointList;
    private List<InfectionCountry> infectionCountryList;

    public String getLastCheckTimeText() {
        return lastCheckTimeText;
    }

    public void setLastCheckTimeText(String lastCheckTimeText) {
        this.lastCheckTimeText = lastCheckTimeText;
    }

    public long getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(long totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public long getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(long totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public long getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(long totalRecovered) {
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