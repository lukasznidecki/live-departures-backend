package com.lnidecki.livedepartures.client;

import com.lnidecki.livedepartures.dto.TtssStopPassagesDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "ttss")
@Produces(MediaType.APPLICATION_JSON)
public interface TtssClient {

    @GET
    @Path("/proxy_bus.php/services/passageInfo/stopPassages/stop")
    TtssStopPassagesDto getBusStopPassages(@QueryParam("stop") String stopId, @QueryParam("mode") String mode);

    @GET
    @Path("/proxy_tram.php/services/passageInfo/stopPassages/stop")
    TtssStopPassagesDto getTramStopPassages(@QueryParam("stop") String stopId, @QueryParam("mode") String mode);
}