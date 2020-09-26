package com.plms.parking.controller;

import com.plms.parking.dtos.request.ParkVehicle;
import com.plms.parking.dtos.response.ExitParkingDTO;
import com.plms.parking.dtos.response.ParkingHistoryDTO;
import com.plms.parking.dtos.response.ParkingResponse;
import com.plms.parking.services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @RequestMapping(value = "/parking/create", method = RequestMethod.POST)
    @ResponseBody
    public ParkingResponse createParking(@RequestBody ParkVehicle parkVehicle) {
        return parkingService.parkVehicle(parkVehicle);
    }

    @RequestMapping(value = "/parking/exit", method = RequestMethod.DELETE)
    @ResponseBody
    public ExitParkingDTO exitParking(@RequestBody ParkVehicle parkVehicle) {
        return parkingService.exitParking(parkVehicle);
    }

    @RequestMapping(value = "/parking/details/{vehicleNum}", method = RequestMethod.GET)
    @ResponseBody
    public ParkingHistoryDTO parkingDetails(@PathVariable("vehicleNum") String vehicleNum) {
        return parkingService.getParkingDetails(vehicleNum);
    }
}
