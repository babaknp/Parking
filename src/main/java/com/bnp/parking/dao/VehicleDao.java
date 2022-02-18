package com.bnp.parking.dao;

import com.bnp.parking.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VehicleDao extends JpaRepository<Vehicle,Long> {
    Vehicle findByCarNumber(String carNumber);
}
