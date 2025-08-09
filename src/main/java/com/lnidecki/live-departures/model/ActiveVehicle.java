package com.example.model;

public class ActiveVehicle {
    public Double bearing;
    public String blockId;
    public String category;
    public Integer currentStatus;
    public String floor;
    public String fullKmkId;
    public String key;
    public String kmkId;
    public Double latitude;
    public Double longitude;
    public String routeShortName;
    public String serviceId;
    public String shift;
    public String source;
    public String stopName;
    public String stopNum;
    public Long timestamp;
    public String tripHeadsign;
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