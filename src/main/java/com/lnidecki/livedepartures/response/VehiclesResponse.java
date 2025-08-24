package com.lnidecki.livedepartures.response;

import com.lnidecki.livedepartures.model.Vehicle;
import java.util.List;

public record VehiclesResponse(List<Vehicle> vehicles) {}