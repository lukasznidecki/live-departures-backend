package com.lnidecki.livedepartures.response;

import com.lnidecki.livedepartures.model.ActiveVehicle;
import java.util.List;

public record ActiveVehiclesResponse(List<ActiveVehicle> vehicles) {}