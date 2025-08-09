package com.example.resource;

import com.example.response.StopsResponse;
import com.example.response.StopTimesResponse;
import com.example.service.DataService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/stops")
@Tag(name = "Stops", description = "Bus and tram stop endpoints")
public class StopResource {

    @Inject
    DataService dataService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all stops", description = "Returns information about all bus and tram stops")
    @APIResponse(responseCode = "200", description = "List of stops")
    public StopsResponse getStops() {
        return new StopsResponse(dataService.getStops());
    }

    @GET
    @Path("/{stop}/current_stop_times")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get current stop times", description = "Returns current departure/arrival times for a specific stop")
    @APIResponse(responseCode = "200", description = "Current stop times")
    public StopTimesResponse getStopTimes(
            @Parameter(description = "Stop ID or stop number", required = true)
            @PathParam("stop") String stop) {
        return new StopTimesResponse(dataService.getStopTimes(stop));
    }
}