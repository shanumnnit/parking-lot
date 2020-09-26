package com.plms.parking.dtos.response;

import lombok.Data;

@Data
public class ParkingHistoryDTO {
    private Integer lotId;
    private Integer areaId;
    private String vehicleNumber;
    private Integer amountPaid;
}
