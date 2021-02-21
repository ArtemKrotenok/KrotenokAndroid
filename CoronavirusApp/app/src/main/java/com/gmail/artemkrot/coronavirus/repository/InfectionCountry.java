package com.gmail.artemkrot.coronavirus.repository;

public class InfectionCountry {
    private String name;
    private long confirmed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(long confirmed) {
        this.confirmed = confirmed;
    }
}