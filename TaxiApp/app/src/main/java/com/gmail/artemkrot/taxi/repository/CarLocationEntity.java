package com.gmail.artemkrot.taxi.repository;

import com.google.android.gms.maps.model.LatLng;

public class CarLocationEntity {
  private long id;
  private LatLng coordinate;
  private String fleetType;
  private String heading;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LatLng getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(LatLng coordinate) {
        this.coordinate = coordinate;
    }

    public String getFleetType() {
        return fleetType;
    }

    public void setFleetType(String fleetType) {
        this.fleetType = fleetType;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}
