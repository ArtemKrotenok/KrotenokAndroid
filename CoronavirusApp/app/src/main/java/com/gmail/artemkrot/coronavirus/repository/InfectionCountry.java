package com.gmail.artemkrot.coronavirus.repository;

public class InfectionCountry {
    private String name;
    private String confirmed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }
}