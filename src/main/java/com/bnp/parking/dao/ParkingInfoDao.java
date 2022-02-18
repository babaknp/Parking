package com.bnp.parking.dao;

import com.bnp.parking.model.ParkingInfo;
import com.bnp.parking.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingInfoDao extends JpaRepository<ParkingInfo,Long> {
    ParkingInfo findByVehicleObjAndOutTime(Vehicle vehicle,String outTime);
    List<ParkingInfo> findByVehicleObjAndCreateDateBetween(Vehicle vehicle, String inDate, String outDate);
}
