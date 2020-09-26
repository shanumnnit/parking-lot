package com.plms.parking.entities;

import lombok.Data;

@Data
public class ParkingHistory {
    private String vehicleNum;
    private Integer lotId;
    private Integer areaId;
    private Integer amount;
    private Long parkingStartTime;
    private boolean exited;
}
