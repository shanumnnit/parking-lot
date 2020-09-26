package com.plms.parking.dtos.request;

import com.plms.parking.entities.Type;
import lombok.Data;

@Data
public class VehicleDTO {
    private String vehicleNumber;
    private Type type;
}
