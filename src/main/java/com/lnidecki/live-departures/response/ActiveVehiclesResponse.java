package com.example.response;

import com.example.model.ActiveVehicle;
import java.util.List;

public class ActiveVehiclesResponse {
    public List<ActiveVehicle> vehicles;

    public ActiveVehiclesResponse() {}

    public ActiveVehiclesResponse(List<ActiveVehicle> vehicles) {
        this.vehicles = vehicles;
    }
}