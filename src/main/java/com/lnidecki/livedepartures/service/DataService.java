package com.lnidecki.livedepartures.service;

import com.lnidecki.livedepartures.client.TtssApiClient;
import com.lnidecki.livedepartures.client.TtssClient;
import com.lnidecki.livedepartures.dto.TtssStopDto;
import com.lnidecki.livedepartures.model.ActiveVehicle;
import com.lnidecki.livedepartures.model.Stop;
import com.lnidecki.livedepartures.model.StopTime;
import com.lnidecki.livedepartures.model.Vehicle;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DataService {

    @Inject
    @RestClient
    TtssApiClient ttssApiClient;

    @Inject
    @RestClient
    TtssClient ttssClient;

    @Inject
    StopCacheService stopCacheService;

    public List<ActiveVehicle> getActiveVehicles() {
        List<ActiveVehicle> vehicles = new ArrayList<>();
        vehicles.add(new ActiveVehicle(225.0, "488", "bus", 2, "low_floor", "BA114",
                "gtfs_mpk_BA114_488_11", "BA114", 50.093624114990234,
                20.063194274902344, "422", "2", "122-01", "gtfs",
                "Darwina", "03", 1754728856L, "Aleja Przyjaźni", "488_11"));
        return vehicles;
    }

    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle("bus", "low_floor", "BA113", "Autosan M09LE Sancity", "BA113", "M09LE"));
        vehicles.add(new Vehicle("bus", "low_floor", "BA114", "Autosan M09LE Sancity", "BA114", "M09LE"));
        vehicles.add(new Vehicle("tram", "low_floor", "3001", "Düwag N8C-NF", "3001", "N8C-NF"));
        return vehicles;
    }

    public List<Stop> getStops() {
        List<Stop> allStops = new ArrayList<>();

        
        List<TtssStopDto> tramStops = ttssApiClient.getStops("t");
        List<Stop> transformedTramStops = tramStops.stream()
                .map(dto -> new Stop(false, dto.lat, dto.lon, dto.name, dto.id, true))
                .toList();

        
        List<TtssStopDto> busStops = ttssApiClient.getStops("b");
        List<Stop> transformedBusStops = busStops.stream()
                .map(dto -> new Stop(true, dto.lat, dto.lon, dto.name, dto.id, false))
                .toList();

        
        allStops.addAll(transformedTramStops);
        allStops.addAll(transformedBusStops);

        return allStops;
    }

    public List<StopTime> getStopTimes(String stopId) {
        List<StopTime> stopTimes = new ArrayList<>();

        try {
            var busPassages = ttssClient.getBusStopPassages(stopId, "departure");
            if (busPassages != null && busPassages.actual != null) {
            stopTimes.addAll(busPassages.actual.stream()
                    .map(passage -> new StopTime(
                            false, 
                            null, 
                            "bus", 
                            passage.actualTime, 
                            false, 
                            stopId + "_" + passage.routeId + "_" + passage.actualTime, 
                            passage.vehicleId, 
                            "PREDICTED".equals(passage.status), 
                            false, 
                            passage.plannedTime, 
                            passage.actualTime, 
                            null, 
                            passage.actualTime, 
                            passage.patternText, 
                            passage.routeId != null ? passage.routeId.toString() : null, 
                            stopId, 
                            true, 
                            passage.direction, 
                            passage.routeId + "_" + passage.actualTime 
                    ))
                    .toList());
            }
        } catch (Exception e) {
            
        }
        
        try {
            var tramPassages = ttssClient.getTramStopPassages(stopId, "departure");
            if (tramPassages != null && tramPassages.actual != null) {
            stopTimes.addAll(tramPassages.actual.stream()
                    .map(passage -> new StopTime(
                            false, 
                            null, 
                            "tram", 
                            passage.actualTime, 
                            false, 
                            stopId + "_" + passage.routeId + "_" + passage.actualTime, 
                            passage.vehicleId, 
                            "PREDICTED".equals(passage.status), 
                            false, 
                            passage.plannedTime, 
                            passage.actualTime, 
                            null, 
                            passage.actualTime, 
                            passage.patternText, 
                            passage.routeId != null ? passage.routeId.toString() : null, 
                            stopId, 
                            true, 
                            passage.direction, 
                            passage.routeId + "_" + passage.actualTime 
                    ))
                    .toList());
            }
        } catch (Exception e) {
            
        }

        return stopTimes;
    }

    public List<StopTime> getDeparturesByStopName(String stopName) {
        List<StopTime> allDepartures = new ArrayList<>();
        
        List<String> stopIds = stopCacheService.getStopIdsByName(stopName);
        
        for (String stopId : stopIds) {
            String ttssStopId = convertToTtssId(stopId);

            
            try {
                var busPassages = ttssClient.getBusStopPassages(ttssStopId, "departure");
                if (busPassages != null && busPassages.actual != null) {
                allDepartures.addAll(busPassages.actual.stream()
                        .map(passage -> new StopTime(
                                false, 
                                null, 
                                "bus", 
                                passage.actualTime, 
                                false, 
                                stopId + "_" + passage.routeId + "_" + passage.actualTime, 
                                passage.vehicleId, 
                                "PREDICTED".equals(passage.status), 
                                false, 
                                passage.plannedTime, 
                                passage.actualTime, 
                                null, 
                                passage.actualTime, 
                                passage.patternText, 
                                passage.routeId != null ? passage.routeId.toString() : null, 
                                stopId, 
                                true, 
                                passage.direction, 
                                passage.routeId + "_" + passage.actualTime 
                        ))
                        .toList());
                }
            } catch (Exception e) {
                
            }
            
            
            try {
                var tramPassages = ttssClient.getTramStopPassages(ttssStopId, "departure");
                if (tramPassages != null && tramPassages.actual != null) {
                allDepartures.addAll(tramPassages.actual.stream()
                        .map(passage -> new StopTime(
                                false, 
                                null, 
                                "tram", 
                                passage.actualTime, 
                                false, 
                                stopId + "_" + passage.routeId + "_" + passage.actualTime, 
                                passage.vehicleId, 
                                "PREDICTED".equals(passage.status), 
                                false, 
                                passage.plannedTime, 
                                passage.actualTime, 
                                null, 
                                passage.actualTime, 
                                passage.patternText, 
                                passage.routeId != null ? passage.routeId.toString() : null, 
                                stopId, 
                                true, 
                                passage.direction, 
                                passage.routeId + "_" + passage.actualTime 
                        ))
                        .toList());
                }
            } catch (Exception e) {
                
            }
        }
        
        return allDepartures;
    }
    
    private String convertToTtssId(String fullId) {
        
        String first3 = fullId.length() >= 4 ? fullId.substring(0, 4) : fullId;
        return String.valueOf(Integer.parseInt(first3));
    }
    
}