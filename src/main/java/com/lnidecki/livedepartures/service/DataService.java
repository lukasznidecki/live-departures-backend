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
        
        try {
            var busVehicles = ttssClient.getBusVehicles("CORRECTED", "ROUTE_BASED", "0");
            if (busVehicles != null && busVehicles.vehicles != null) {
                for (var vehicle : busVehicles.vehicles) {
                    if (vehicle.isDeleted == null || !vehicle.isDeleted) {
                        String routeNumber = extractRouteNumber(vehicle.name);
                        String destination = extractDestination(vehicle.name);
                        
                        vehicles.add(new ActiveVehicle(
                            vehicle.heading,
                            vehicle.name,
                            vehicle.category,
                            0,
                            "unknown",
                            vehicle.id,
                            vehicle.tripId,
                            vehicle.id,
                            vehicle.latitude != null ? vehicle.latitude / 3600000.0 : null,
                            vehicle.longitude != null ? vehicle.longitude / 3600000.0 : null,
                            routeNumber,
                            "1",
                            vehicle.id,
                            "ttss",
                            vehicle.name,
                            vehicle.color,
                            System.currentTimeMillis(),
                            destination,
                            vehicle.tripId
                        ));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to load bus vehicles: " + e.getMessage());
        }
        
        try {
            var tramVehicles = ttssClient.getTramVehicles("CORRECTED", "ROUTE_BASED", "0");
            if (tramVehicles != null && tramVehicles.vehicles != null) {
                for (var vehicle : tramVehicles.vehicles) {
                    if (vehicle.isDeleted == null || !vehicle.isDeleted) {
                        String routeNumber = extractRouteNumber(vehicle.name);
                        String destination = extractDestination(vehicle.name);
                        
                        vehicles.add(new ActiveVehicle(
                            vehicle.heading,
                            vehicle.name,
                            vehicle.category,
                            0,
                            "unknown",
                            vehicle.id,
                            vehicle.tripId,
                            vehicle.id,
                            vehicle.latitude != null ? vehicle.latitude / 3600000.0 : null,
                            vehicle.longitude != null ? vehicle.longitude / 3600000.0 : null,
                            routeNumber,
                            "1",
                            vehicle.id,
                            "ttss",
                            vehicle.name,
                            vehicle.color,
                            System.currentTimeMillis(),
                            destination,
                            vehicle.tripId
                        ));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to load tram vehicles: " + e.getMessage());
        }
        
        return vehicles;
    }

    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        
        try {
            var busVehicles = ttssClient.getBusVehicles("CORRECTED", "ROUTE_BASED", "0");
            if (busVehicles != null && busVehicles.vehicles != null) {
                for (var vehicle : busVehicles.vehicles) {
                    if (vehicle.isDeleted == null || !vehicle.isDeleted) {
                        vehicles.add(new Vehicle(
                            vehicle.category,
                            "unknown",
                            vehicle.id,
                            "Bus " + vehicle.name,
                            vehicle.id,
                            vehicle.category
                        ));
                    }
                }
            }
        } catch (Exception e) {
        }
        
        try {
            var tramVehicles = ttssClient.getTramVehicles("CORRECTED", "ROUTE_BASED", "0");
            if (tramVehicles != null && tramVehicles.vehicles != null) {
                for (var vehicle : tramVehicles.vehicles) {
                    if (vehicle.isDeleted == null || !vehicle.isDeleted) {
                        vehicles.add(new Vehicle(
                            vehicle.category,
                            "unknown",
                            vehicle.id,
                            "Tram " + vehicle.name,
                            vehicle.id,
                            vehicle.category
                        ));
                    }
                }
            }
        } catch (Exception e) {
        }
        
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
    
    private String extractRouteNumber(String vehicleName) {
        if (vehicleName == null || vehicleName.isEmpty()) {
            return "";
        }
        int spaceIndex = vehicleName.indexOf(' ');
        if (spaceIndex > 0) {
            return vehicleName.substring(0, spaceIndex);
        }
        return vehicleName;
    }
    
    private String extractDestination(String vehicleName) {
        if (vehicleName == null || vehicleName.isEmpty()) {
            return "";
        }
        int spaceIndex = vehicleName.indexOf(' ');
        if (spaceIndex > 0 && spaceIndex < vehicleName.length() - 1) {
            return vehicleName.substring(spaceIndex + 1);
        }
        return "";
    }
    
}