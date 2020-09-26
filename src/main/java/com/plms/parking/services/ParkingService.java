package com.plms.parking.services;

import com.plms.parking.dtos.request.ParkVehicle;
import com.plms.parking.dtos.response.ExitParkingDTO;
import com.plms.parking.dtos.response.ParkingHistoryDTO;
import com.plms.parking.dtos.response.ParkingResponse;
import com.plms.parking.entities.Area;
import com.plms.parking.entities.ParkingHistory;
import com.plms.parking.entities.Type;
import com.plms.parking.persistence.ParkingDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingService {

    @Autowired
    private ParkingDB parkingDB;

    public ParkingResponse parkVehicle(ParkVehicle parkVehicle) {
        ParkingResponse parkingResponse = new ParkingResponse();
        boolean vehicleAlreadyExists = parkingDB.vehicleAlreadyExists(parkVehicle.getVehicle().getVehicleNumber());
        Area availableArea = parkingDB.getAvailableArea(parkVehicle.getVehicle().getType());
        if (availableArea == null || vehicleAlreadyExists) {
            if (vehicleAlreadyExists) parkingResponse.setErrorMsg("Vehicle Already exists");
            else
                parkingResponse.setErrorMsg("Parking full");
            return parkingResponse;
        }
        parkingDB.decrementCapacity(availableArea);
        parkingDB.updateParkingHistory(parkVehicle, availableArea);
        parkingResponse.setAreaId(availableArea.getId());
        parkingResponse.setLotId(availableArea.getLotId());
        return parkingResponse;
    }

    public ExitParkingDTO exitParking(ParkVehicle parkVehicle) {
        ParkingHistory parkingHistory = parkingDB.parkingExit(parkVehicle);
        ExitParkingDTO exitParkingDTO = new ExitParkingDTO();
        exitParkingDTO.setAreaId(parkingHistory.getAreaId());
        exitParkingDTO.setAmountPending((int) getParkingAmount(parkingHistory, parkVehicle));
        exitParkingDTO.setLotId(parkingHistory.getLotId());

        parkingDB.updateParkingAmount(parkVehicle.getVehicle().getVehicleNumber(), exitParkingDTO.getAmountPending());
        return exitParkingDTO;
    }

    public ParkingHistoryDTO getParkingDetails(String vehicleNum) {
        ParkingHistory parkingHistory = parkingDB.parkingDetails(vehicleNum);
        ParkingHistoryDTO parkingHistoryDTO = new ParkingHistoryDTO();
        parkingHistoryDTO.setAreaId(parkingHistory.getAreaId());
        parkingHistoryDTO.setLotId(parkingHistory.getLotId());
        parkingHistoryDTO.setVehicleNumber(vehicleNum);
        parkingHistoryDTO.setAmountPaid(parkingHistory.getAmount());
        return parkingHistoryDTO;
    }

    private double getParkingAmount(ParkingHistory parkingHistory, ParkVehicle parkVehicle) {
        int minutesParked = (int) ((System.currentTimeMillis() - parkingHistory.getParkingStartTime()) / (1000));
        if (Type.TWO_WHEELER.equals(parkVehicle.getVehicle().getType())) {
            return minutesParked * 0.5;
        }
        return minutesParked * 1;
    }
}
