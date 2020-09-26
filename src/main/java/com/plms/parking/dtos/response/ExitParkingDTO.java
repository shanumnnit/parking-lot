package com.plms.parking.dtos.response;

import lombok.Data;

@Data
public class ExitParkingDTO {
    private Integer lotId;
    private Integer areaId;
    private Integer amountPending;
}
