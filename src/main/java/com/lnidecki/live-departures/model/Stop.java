package com.example.model;

public class Stop {
    public Boolean bus;
    public Double stopLat;
    public Double stopLon;
    public String stopName;
    public String stopNum;
    public Boolean tram;

    public Stop() {}

    public Stop(Boolean bus, Double stopLat, Double stopLon, String stopName, 
                String stopNum, Boolean tram) {
        this.bus = bus;
        this.stopLat = stopLat;
        this.stopLon = stopLon;
        this.stopName = stopName;
        this.stopNum = stopNum;
        this.tram = tram;
    }
}