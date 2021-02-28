package com.gmail.artemkrot.taxi.repository;

import java.util.List;

public class CarLocationRepository {

    private static CarLocationRepository instance;
    private List<CarLocationEntity> carLocationsList;
    private boolean isReady;

    private CarLocationRepository() {
        this.isReady = false;
    }

    public static CarLocationRepository getInstance() {
        if (instance == null) {
            instance = new CarLocationRepository();
        }
        return instance;
    }

    public List<CarLocationEntity> getCarLocationsList() {
        return carLocationsList;
    }

    public void setCarLocationsList(List<CarLocationEntity> carLocationsList) {
        this.isReady = true;
        this.carLocationsList = carLocationsList;
    }

    public boolean isReady() {
        return isReady;
    }
}