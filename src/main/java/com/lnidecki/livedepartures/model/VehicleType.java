package com.lnidecki.livedepartures.model;

/**
 * Represents types of transit vehicles.
 */
public enum VehicleType {
    BUS("bus"),
    TRAM("tram");

    private final String type;

    VehicleType(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
