package com.plms.parking.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plms.parking.dtos.request.ParkVehicle;
import com.plms.parking.entities.Area;
import com.plms.parking.entities.ParkingHistory;
import com.plms.parking.entities.ParkingLot;
import com.plms.parking.entities.Type;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class ParkingDB {
    private HashMap<Integer, ParkingLot> parkingLots;
    private HashMap<String, ParkingHistory> vehicleToParkingHistory;

    @Value("${seed.data}")
    private String seedData;

    @PostConstruct
    public void init() {
        Gson gson = new Gson();
        parkingLots = gson.fromJson(seedData, new TypeToken<HashMap<Integer, ParkingLot>>() {
        }.getType());
        vehicleToParkingHistory = new HashMap<>();
    }

    public Area getAvailableArea(Type vehicleType) {
        for (Integer parkingLotId : parkingLots.keySet()) {
            Area availableArea = parkingLots.get(parkingLotId).getAreas().stream().filter(area -> vehicleType.equals(area.getType())).findFirst().get();
            if (availableArea.getCapacity() > 0)
                return availableArea;
        }
        return null;
    }

    public void decrementCapacity(Area area) {
        area.setCapacity(Math.max(0, area.getCapacity() - 1));
    }

    public void updateParkingHistory(ParkVehicle parkVehicle, Area availableArea) {
        ParkingHistory history = new ParkingHistory();
        history.setAmount(0);
        history.setAreaId(availableArea.getId());
        history.setLotId(availableArea.getLotId());
        history.setParkingStartTime(System.currentTimeMillis());
        vehicleToParkingHistory.putIfAbsent(parkVehicle.getVehicle().getVehicleNumber(), history);
    }

    public ParkingHistory parkingExit(ParkVehicle parkVehicle) {
        ParkingHistory parkingHistory = vehicleToParkingHistory.get(parkVehicle.getVehicle().getVehicleNumber());
        parkingHistory.setAmount((int) ((System.currentTimeMillis() - parkingHistory.getParkingStartTime()) / 1000));
        parkingHistory.setExited(true);
        return parkingHistory;
    }

    public ParkingHistory parkingDetails(String vehicleNum) {
        ParkingHistory parkingHistory = vehicleToParkingHistory.get(vehicleNum);
        return parkingHistory;
    }

    public void updateParkingAmount(String vehicleNum, Integer amountPaid) {
        vehicleToParkingHistory.get(vehicleNum).setAmount(amountPaid);
    }

    public boolean vehicleAlreadyExists(String vehicleNumber) {
        return vehicleToParkingHistory.containsKey(vehicleNumber);
    }
}
