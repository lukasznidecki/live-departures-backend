package com.example.response;

import com.example.model.Vehicle;
import java.util.List;

public class VehiclesResponse {
    public List<Vehicle> vehicles;

    public VehiclesResponse() {}

    public VehiclesResponse(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}