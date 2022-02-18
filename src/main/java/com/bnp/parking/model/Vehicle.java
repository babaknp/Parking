package com.bnp.parking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "VEHICLES",schema = "SCHEMA_PRK")
public class Vehicle {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vehicleId;
    @Column(nullable = false)
    private String carNumber;
    @Column(nullable = false)
    public VehicleType vehicleType;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ParkingInfo> parkingInfoList;

    public Vehicle() {
    }

    public Vehicle(String carNumber, VehicleType vehicleType) {
        this.carNumber=carNumber;
        this.vehicleType=vehicleType;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }


}
