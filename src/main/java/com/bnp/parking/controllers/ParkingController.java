package com.bnp.parking.controllers;

import com.bnp.parking.model.EntryRegistrationInput;
import com.bnp.parking.model.ParkingInfo;
import com.bnp.parking.model.PriceRate;
import com.bnp.parking.model.VehicleReportInput;
import com.bnp.parking.service.ParkingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/parking")
public class ParkingController {
    @Autowired
    private ParkingServiceImpl parkingService;

    @PostMapping(value = "/entryRegistration")
    public ResponseEntity<ParkingInfo> entryRegistration(@RequestBody EntryRegistrationInput registrationInput) throws Exception {
        try {
            return parkingService.entryRegistration(registrationInput);
        } catch (Exception e) {
            return new ResponseEntity<ParkingInfo>(HttpStatus.EXPECTATION_FAILED);
        }
    }


    @PostMapping(value = "/exitVehicle")
    public ResponseEntity<ParkingInfo> exitVehicle(@RequestParam String carNumber) throws Exception {
        try {
            return parkingService.exitVehicle(carNumber);
        } catch (Exception e) {
            return new ResponseEntity<ParkingInfo>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value = "/getVehicleReport")
    public ResponseEntity<List<ParkingInfo>> vehicleRepor(@RequestBody VehicleReportInput vehicleReportInput) {
        return parkingService.getVehicleReport(vehicleReportInput);
    }
}
