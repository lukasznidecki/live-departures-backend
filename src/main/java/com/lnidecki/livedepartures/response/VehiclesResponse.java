package com.lnidecki.livedepartures.response;

import com.lnidecki.livedepartures.model.Vehicle;
import java.util.List;

public class VehiclesResponse {
    public List<Vehicle> vehicles;

    public VehiclesResponse() {}

    public VehiclesResponse(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}