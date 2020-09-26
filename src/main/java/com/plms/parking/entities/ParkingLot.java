package com.plms.parking.entities;

import lombok.Data;

import java.util.List;

@Data
public class ParkingLot {
    private Integer id;
    private List<Area> areas;
}
