package com.lnidecki.livedepartures.service;

import com.lnidecki.livedepartures.client.TtssApiClient;
import com.lnidecki.livedepartures.client.TtssClient;
import com.lnidecki.livedepartures.dto.TtssVehicleDto;
import com.lnidecki.livedepartures.dto.TtssVehiclesResponseDto;
import com.lnidecki.livedepartures.model.ActiveVehicle;
import com.lnidecki.livedepartures.model.Stop;
import com.lnidecki.livedepartures.model.StopTime;
import com.lnidecki.livedepartures.model.Vehicle;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@ApplicationScoped
public class TransitDataService {

    @Inject
    @RestClient
    TtssApiClient ttssApiClient;

    @Inject
    @RestClient
    TtssClient ttssClient;

    @Inject
    StopCacheService stopCacheService;

    public List<ActiveVehicle> getActiveVehicles() {
        return Stream.of(
                        fetchVehicles(() -> ttssClient.getBusVehicles("CORRECTED", "ROUTE_BASED", "0"), "bus"),
                        fetchVehicles(() -> ttssClient.getTramVehicles("CORRECTED", "ROUTE_BASED", "0"), "tram")
                )
                .flatMap(List::stream)
                .toList();
    }

    public List<Vehicle> getVehicles() {
        var busVehicles = getVehiclesByType("bus");
        var tramVehicles = getVehiclesByType("tram");
        return Stream.of(busVehicles, tramVehicles)
                .flatMap(List::stream)
                .toList();
    }

    public List<Stop> getStops() {
        var tramStops = ttssApiClient.getStops("t").stream()
                .map(dto -> Stop.builder()
                        .bus(false)
                        .stopLat(dto.lat())
                        .stopLon(dto.lon())
                        .stopName(dto.name())
                        .stopNum(dto.id())
                        .tram(true)
                        .build())
                .toList();

        var busStops = ttssApiClient.getStops("b").stream()
                .map(dto -> Stop.builder()
                        .bus(true)
                        .stopLat(dto.lat())
                        .stopLon(dto.lon())
                        .stopName(dto.name())
                        .stopNum(dto.id())
                        .tram(false)
                        .build())
                .toList();

        return Stream.of(tramStops, busStops)
                .flatMap(List::stream)
                .toList();
    }

    public List<StopTime> getStopTimes(String stopId) {
        var busStopTimes = getStopTimesByType("bus", stopId, stopId);
        var tramStopTimes = getStopTimesByType("tram", stopId, stopId);
        return Stream.of(busStopTimes, tramStopTimes)
                .flatMap(List::stream)
                .toList();
    }

    public List<StopTime> getDeparturesByStopName(String stopName) {
        return stopCacheService.getStopIdsByName(stopName).stream()
                .map(this::convertToTtssId)
                .flatMap(ttssStopId ->
                        Stream.of(
                                getStopTimesByType("bus", ttssStopId, stopName),
                                getStopTimesByType("tram", ttssStopId, stopName)
                        ).flatMap(List::stream)
                )
                .toList();
    }

    private List<ActiveVehicle> fetchVehicles(Supplier<TtssVehiclesResponseDto> vehicleSupplier, String vehicleType) {
        try {
            var response = vehicleSupplier.get();
            if (response != null && response.vehicles() != null) {
                return response.vehicles().stream()
                        .filter(vehicle -> vehicle.isDeleted() == null || !vehicle.isDeleted())
                        .map(vehicle -> createActiveVehicle(vehicle, vehicleType))
                        .toList();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch " + vehicleType + " vehicles from TTSS API: " + e.getMessage(), e);
        }
        return List.of();
    }

    private ActiveVehicle createActiveVehicle(TtssVehicleDto vehicle, String vehicleType) {
        return ActiveVehicle.builder()
                .bearing(vehicle.heading())
                .blockId(vehicle.name())
                .category(vehicle.category())
                .currentStatus(0)
                .floor("unknown")
                .fullKmkId(vehicle.id())
                .key(vehicle.tripId())
                .kmkId(vehicle.id())
                .latitude(vehicle.latitude() != null ? vehicle.latitude() / 3600000.0 : null)
                .longitude(vehicle.longitude() != null ? vehicle.longitude() / 3600000.0 : null)
                .routeShortName(extractRouteNumber(vehicle.name()))
                .serviceId("1")
                .shift(vehicle.id())
                .source("ttss")
                .stopName(vehicle.name())
                .stopNum(vehicle.color())
                .timestamp(System.currentTimeMillis())
                .tripHeadsign(extractDestination(vehicle.name()))
                .tripId(vehicle.tripId())
                .build();
    }

    private List<Vehicle> getVehiclesByType(String type) {
        try {
            var response = switch (type) {
                case "bus" -> ttssClient.getBusVehicles("CORRECTED", "ROUTE_BASED", "0");
                case "tram" -> ttssClient.getTramVehicles("CORRECTED", "ROUTE_BASED", "0");
                default -> null;
            };

            if (response != null && response.vehicles() != null) {
                return response.vehicles().stream()
                        .filter(vehicle -> vehicle.isDeleted() == null || !vehicle.isDeleted())
                        .map(vehicle -> Vehicle.builder()
                                .category(vehicle.category())
                                .floor("unknown")
                                .fullKmkId(vehicle.id())
                                .fullModelName(switch (type) {
                                    case "bus" -> "Bus " + vehicle.name();
                                    case "tram" -> "Tram " + vehicle.name();
                                    default -> vehicle.name();
                                })
                                .kmkId(vehicle.id())
                                .shortModelName(vehicle.category())
                                .build())
                        .toList();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve " + type + " vehicles data from TTSS client: " + e.getMessage(), e);
        }
        return List.of();
    }

    private List<StopTime> getStopTimesByType(String vehicleType, String ttssStopId, String stopId) {
        try {
            var passages = switch (vehicleType) {
                case "bus" -> ttssClient.getBusStopPassages(ttssStopId, "departure");
                case "tram" -> ttssClient.getTramStopPassages(ttssStopId, "departure");
                default -> null;
            };

            if (passages != null && passages.actual() != null) {
                return passages.actual().stream()
                        .map(passage -> StopTime.builder()
                                .arrival(false)
                                .blockId(null)
                                .category(vehicleType)
                                .departureTimestamp(passage.actualTime())
                                .hidden(false)
                                .key(stopId + "_" + passage.routeId() + "_" + passage.actualTime())
                                .kmkId(passage.vehicleId())
                                .live("PREDICTED".equals(passage.status()))
                                .old(false)
                                .plannedDepartureTime(passage.plannedTime())
                                .plannedDepartureTimestamp(passage.actualTime())
                                .predictedDelay(null)
                                .predictedDepartureTimestamp(passage.actualTime())
                                .routeShortName(passage.patternText())
                                .serviceId(passage.routeId() != null ? passage.routeId().toString() : null)
                                .stopNum(stopId)
                                .stopping(true)
                                .tripHeadsign(passage.direction())
                                .tripId(passage.routeId() + "_" + passage.actualTime())
                                .build())
                        .toList();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get " + vehicleType + " stop passages for stop " + ttssStopId + ": " + e.getMessage(), e);
        }
        return List.of();
    }

    private String convertToTtssId(String fullId) {
        var prefix = fullId.length() >= 4 ? fullId.substring(0, 4) : fullId;
        return String.valueOf(Integer.parseInt(prefix));
    }

    private String extractRouteNumber(String vehicleName) {
        return switch (vehicleName) {
            case null -> "";
            case String name when name.isEmpty() -> "";
            case String name when name.contains(" ") -> name.substring(0, name.indexOf(' '));
            default -> vehicleName;
        };
    }

    private String extractDestination(String vehicleName) {
        return switch (vehicleName) {
            case String name when name.contains(" ") && name.indexOf(' ') < name.length() - 1 ->
                    name.substring(name.indexOf(' ') + 1);
            case null, default -> "";
        };
    }

}