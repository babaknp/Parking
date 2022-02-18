package com.bnp.parking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


@Entity
@Table(name = "PAYMENT",schema = "SCHEMA_PRK")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long paymentId;

    private String pan;
    private Long traceNumber;
    private Long transactionAmount;
    private String insertDate;
    private PaymentStatus paymentStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parkingInfoId")
    @JsonIgnore
    private ParkingInfo parkingInfoObj;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public Long getTraceNumber() {
        return traceNumber;
    }

    public void setTraceNumber(Long traceNumber) {
        this.traceNumber = traceNumber;
    }

    public Long getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public ParkingInfo getParkingInfoObj() {
        return parkingInfoObj;
    }

    public void setParkingInfoObj(ParkingInfo parkingInfoObj) {
        this.parkingInfoObj = parkingInfoObj;
    }

}
