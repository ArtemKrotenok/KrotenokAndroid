package com.gmail.artemkrot.coronavirus.repository;

public class CoronaVirusDataRepository {

    private CoronaVirusDataEntity coronaVirusData;
    private static CoronaVirusDataRepository instance;
    private boolean isActual;

    private CoronaVirusDataRepository() {
        this.isActual = false;
    }

    public static CoronaVirusDataRepository getInstance() {
        if (instance == null) {
            instance = new CoronaVirusDataRepository();
        }
        return instance;
    }

    public CoronaVirusDataEntity getCoronaVirusData() {
        return coronaVirusData;
    }

    public void setCoronaVirusData(CoronaVirusDataEntity coronaVirusData) {
        this.coronaVirusData = coronaVirusData;
        this.isActual = true;
    }

    public boolean isActual() {
        return isActual;
    }
}