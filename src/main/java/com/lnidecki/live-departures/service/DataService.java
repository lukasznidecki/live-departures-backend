package com.example.service;

import com.example.model.ActiveVehicle;
import com.example.model.Stop;
import com.example.model.StopTime;
import com.example.model.Vehicle;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DataService {

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
        List<Stop> stops = new ArrayList<>();
        stops.add(new Stop(true, 50.064276111, 19.924364444, "AGH / UR", "01", false));
        stops.add(new Stop(true, 50.062825555, 19.923145, "AGH / UR", "02", false));
        stops.add(new Stop(false, 50.073967, 19.99881, "AKF / PK", "01", true));
        return stops;
    }

    public List<StopTime> getStopTimes(String stopId) {
        List<StopTime> stopTimes = new ArrayList<>();
        stopTimes.add(new StopTime(false, "676", "bus", 1754728980L, false, 
                                  "bus_676_6_2_BR508", "BR508", false, true, "10:43:00", 
                                  1754728980L, null, null, "301", "2", "04", false, 
                                  "Niepołomice Dworzec", "676_6"));
        return stopTimes;
    }
}