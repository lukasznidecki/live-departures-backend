package com.lnidecki.livedepartures.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StopTime {
    @JsonProperty("arrival")
    public Boolean arrival;
    @JsonProperty("block_id")
    public String blockId;
    @JsonProperty("category")
    public String category;
    @JsonProperty("departure_timestamp")
    public String departureTimestamp;
    @JsonProperty("hidden")
    public Boolean hidden;
    @JsonProperty("key")
    public String key;
    @JsonProperty("kmk_id")
    public String kmkId;
    @JsonProperty("live")
    public Boolean live;
    @JsonProperty("old")
    public Boolean old;
    @JsonProperty("planned_departure_time")
    public String plannedDepartureTime;
    @JsonProperty("planned_departure_timestamp")
    public String plannedDepartureTimestamp;
    @JsonProperty("predicted_delay")
    public Double predictedDelay;
    @JsonProperty("predicted_departure_timestamp")
    public String predictedDepartureTimestamp;
    @JsonProperty("route_short_name")
    public String routeShortName;
    @JsonProperty("service_id")
    public String serviceId;
    @JsonProperty("stop_num")
    public String stopNum;
    @JsonProperty("stopping")
    public Boolean stopping;
    @JsonProperty("trip_headsign")
    public String tripHeadsign;
    @JsonProperty("trip_id")
    public String tripId;

    public StopTime() {}

    public StopTime(Boolean arrival, String blockId, String category, String departureTimestamp,
                   Boolean hidden, String key, String kmkId, Boolean live, Boolean old,
                   String plannedDepartureTime, String plannedDepartureTimestamp,
                   Double predictedDelay, String predictedDepartureTimestamp,
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