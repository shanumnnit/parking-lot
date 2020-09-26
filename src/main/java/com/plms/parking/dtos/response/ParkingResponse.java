package com.plms.parking.dtos.response;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingResponse {
    private Integer lotId;
    private Integer areaId;
    private String errorMsg;
}
