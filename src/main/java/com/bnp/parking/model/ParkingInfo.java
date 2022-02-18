package com.bnp.parking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "PARKING_INFO",schema = "SCHEMA_PRK")
public class ParkingInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long parkingInfoId;
    private String inTime;
    private String outTime;
    private Long totalAmount;
    private PaymentStatus paymentStatus;
    private String createDate;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicleId")
    private Vehicle vehicleObj;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priceRateId")
    private PriceRate priceRateObj;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Payment> paymentList;
    public ParkingInfo() {
    }

    public ParkingInfo(Vehicle vehicle, PriceRate priceRate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.inTime = dtf.format(now);
        this.totalAmount = priceRate.getEntryPrice();
        this.paymentStatus = PaymentStatus.UnSuccess;
        this.vehicleObj = vehicle;
        this.priceRateObj = priceRate;
        this.createDate = LocalDateTime.now().toLocalDate().toString();
    }

    public Long getParkingInfoId() {
        return parkingInfoId;
    }

    public void setParkingInfoId(Long parkingInfoId) {
        this.parkingInfoId = parkingInfoId;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Vehicle getVehicleObj() {
        return vehicleObj;
    }

    public void setVehicleObj(Vehicle vehicleObj) {
        this.vehicleObj = vehicleObj;
    }

    public PriceRate getPriceRateObj() {
        return priceRateObj;
    }

    public void setPriceRateObj(PriceRate priceRateObj) {
        this.priceRateObj = priceRateObj;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Set<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(Set<Payment> paymentList) {
        this.paymentList = paymentList;
    }
}
