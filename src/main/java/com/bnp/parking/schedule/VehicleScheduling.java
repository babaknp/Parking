package com.bnp.parking.schedule;


import com.bnp.parking.service.ParkingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class VehicleScheduling {
    @Autowired
    ApplicationContext applicationContext;

    @Scheduled(fixedDelay = 5000)
    public void fillVehicle() {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            for (int i = 0; i < 3; i++) {
                VehicleThread vehicleThread = new VehicleThread();
                applicationContext.getAutowireCapableBeanFactory().autowireBean(vehicleThread);
                executorService.execute(vehicleThread);
                System.out.println("running..." + i);
            }
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
