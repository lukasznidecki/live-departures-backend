package com.lnidecki.livedepartures.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActiveVehicle {
    @JsonProperty("bearing")
    public Double bearing;
    @JsonProperty("block_id")
    public String blockId;
    @JsonProperty("category")
    public String category;
    @JsonProperty("current_status")
    public Integer currentStatus;
    @JsonProperty("floor")
    public String floor;
    @JsonProperty("full_kmk_id")
    public String fullKmkId;
    @JsonProperty("key")
    public String key;
    @JsonProperty("kmk_id")
    public String kmkId;
    @JsonProperty("latitude")
    public Double latitude;
    @JsonProperty("longitude")
    public Double longitude;
    @JsonProperty("route_short_name")
    public String routeShortName;
    @JsonProperty("service_id")
    public String serviceId;
    @JsonProperty("shift")
    public String shift;
    @JsonProperty("source")
    public String source;
    @JsonProperty("stop_name")
    public String stopName;
    @JsonProperty("stop_num")
    public String stopNum;
    @JsonProperty("timestamp")
    public Long timestamp;
    @JsonProperty("trip_headsign")
    public String tripHeadsign;
    @JsonProperty("trip_id")
    public String tripId;

    public ActiveVehicle() {}

    public ActiveVehicle(Double bearing, String blockId, String category, Integer currentStatus,
                        String floor, String fullKmkId, String key, String kmkId, Double latitude,
                        Double longitude, String routeShortName, String serviceId, String shift,
                        String source, String stopName, String stopNum, Long timestamp,
                        String tripHeadsign, String tripId) {
        this.bearing = bearing;
        this.blockId = blockId;
        this.category = category;
        this.currentStatus = currentStatus;
        this.floor = floor;
        this.fullKmkId = fullKmkId;
        this.key = key;
        this.kmkId = kmkId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.routeShortName = routeShortName;
        this.serviceId = serviceId;
        this.shift = shift;
        this.source = source;
        this.stopName = stopName;
        this.stopNum = stopNum;
        this.timestamp = timestamp;
        this.tripHeadsign = tripHeadsign;
        this.tripId = tripId;
    }
}