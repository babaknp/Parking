package com.bnp.parking.schedule;

import com.bnp.parking.common.Utils;
import com.bnp.parking.model.EntryRegistrationInput;
import com.bnp.parking.model.VehicleType;
import com.bnp.parking.service.ParkingServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
@Component
public class VehicleThread implements Runnable {
    @Autowired
    private ParkingServiceImpl parkingService;

    @Override
    @Bean
    public void run() {
        try {
            EntryRegistrationInput entryRegistrationInput = new EntryRegistrationInput();
            entryRegistrationInput.setCarNumber(Utils.generateRandomCarNumber());
            entryRegistrationInput.setVehicleType(Utils.getRandomVehicleType());
            parkingService.entryRegistration(entryRegistrationInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
