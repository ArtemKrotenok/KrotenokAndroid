package com.gmail.artemkrot.coronavirus.repository;

public class InfectionPointEntity {

    private String name;
    private double navLat;
    private double navLong;
    private long confirmed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNavLat() {
        return navLat;
    }

    public void setNavLat(double navLat) {
        this.navLat = navLat;
    }

    public double getNavLong() {
        return navLong;
    }

    public void setNavLong(double navLong) {
        this.navLong = navLong;
    }

    public long getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(long confirmed) {
        this.confirmed = confirmed;
    }
}
