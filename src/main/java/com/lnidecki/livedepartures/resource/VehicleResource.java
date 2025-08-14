package com.lnidecki.livedepartures.resource;

import com.lnidecki.livedepartures.response.ActiveVehiclesResponse;
import com.lnidecki.livedepartures.response.VehiclesResponse;
import com.lnidecki.livedepartures.service.DataService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/vehicles")
@Tag(name = "Vehicles", description = "Vehicle information endpoints")
public class VehicleResource {

    @Inject
    DataService dataService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all vehicles", description = "Returns information about all vehicles in the fleet")
    @APIResponse(responseCode = "200", description = "List of vehicles")
    public VehiclesResponse getVehicles() {
        return new VehiclesResponse(dataService.getVehicles());
    }

    @GET
    @Path("/active/gtfs")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get active vehicles", description = "Returns real-time information about currently active vehicles with GPS coordinates")
    @APIResponse(responseCode = "200", description = "List of active vehicles with real-time data")
    public ActiveVehiclesResponse getActiveVehicles() {
        return new ActiveVehiclesResponse(dataService.getActiveVehicles());
    }
}