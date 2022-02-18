package com.bnp.parking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PRICE_RATES",schema = "SCHEMA_PRK")
public class PriceRate {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long priceRateId;
    private Long entryPrice;
    private Long hourlyRate;
    private Long dailyRate;
    private Long monthlyRate;
    private Status status;
    @JsonIgnore
    private LocalDateTime insertDate;
    public VehicleType vehicleType;
    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ParkingInfo> parkingInfoList;

    public Long getPriceRateId() {
        return priceRateId;
    }

    public void setPriceRateId(Long priceRateId) {
        this.priceRateId = priceRateId;
    }

    public Long getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(Long entryPrice) {
        this.entryPrice = entryPrice;
    }

    public Long getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Long hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Long getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(Long dailyRate) {
        this.dailyRate = dailyRate;
    }

    public Long getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(Long monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public List<ParkingInfo> getParkingInfoList() {
        return parkingInfoList;
    }

    public void setParkingInfoList(List<ParkingInfo> parkingInfoList) {
        this.parkingInfoList = parkingInfoList;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getInsertDate() {

        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }


}
