package com.lnidecki.livedepartures.client;

import com.lnidecki.livedepartures.dto.TtssStopDto;
import com.lnidecki.livedepartures.dto.TtssStopPassagesDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "ttss-api")
@Produces(MediaType.APPLICATION_JSON)
public interface TtssApiClient {

    @GET
    @Path("/stops")
    List<TtssStopDto> getStops(@QueryParam("type") String type);
}