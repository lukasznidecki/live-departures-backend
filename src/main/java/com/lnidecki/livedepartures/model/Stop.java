package com.lnidecki.livedepartures.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stop {
    @JsonProperty("bus")
    public Boolean bus;
    @JsonProperty("stop_lat")
    public Double stopLat;
    @JsonProperty("stop_lon")
    public Double stopLon;
    @JsonProperty("stop_name")
    public String stopName;
    @JsonProperty("stop_num")
    public String stopNum;
    @JsonProperty("tram")
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