package com.example.model;

public class StopTime {
    public Boolean arrival;
    public String blockId;
    public String category;
    public Long departureTimestamp;
    public Boolean hidden;
    public String key;
    public String kmkId;
    public Boolean live;
    public Boolean old;
    public String plannedDepartureTime;
    public Long plannedDepartureTimestamp;
    public Double predictedDelay;
    public Double predictedDepartureTimestamp;
    public String routeShortName;
    public String serviceId;
    public String stopNum;
    public Boolean stopping;
    public String tripHeadsign;
    public String tripId;

    public StopTime() {}

    public StopTime(Boolean arrival, String blockId, String category, Long departureTimestamp,
                   Boolean hidden, String key, String kmkId, Boolean live, Boolean old,
                   String plannedDepartureTime, Long plannedDepartureTimestamp,
                   Double predictedDelay, Double predictedDepartureTimestamp,
                   String routeShortName, String serviceId, String stopNum, Boolean stopping,
                   String tripHeadsign, String tripId) {
        this.arrival = arrival;
        this.blockId = blockId;
        this.category = category;
        this.departureTimestamp = departureTimestamp;
        this.hidden = hidden;
        this.key = key;
        this.kmkId = kmkId;
        this.live = live;
        this.old = old;
        this.plannedDepartureTime = plannedDepartureTime;
        this.plannedDepartureTimestamp = plannedDepartureTimestamp;
        this.predictedDelay = predictedDelay;
        this.predictedDepartureTimestamp = predictedDepartureTimestamp;
        this.routeShortName = routeShortName;
        this.serviceId = serviceId;
        this.stopNum = stopNum;
        this.stopping = stopping;
        this.tripHeadsign = tripHeadsign;
        this.tripId = tripId;
    }
}