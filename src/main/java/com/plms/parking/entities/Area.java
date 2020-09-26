package com.plms.parking.entities;

import lombok.Data;

@Data
public class Area {
    private Integer id;
    private Integer capacity;
    private Type type;
    private Integer lotId;
}
