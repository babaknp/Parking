package com.bnp.parking.dao;

import com.bnp.parking.model.ParkingInfo;
import com.bnp.parking.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDao extends JpaRepository<Payment,Long> {

}
