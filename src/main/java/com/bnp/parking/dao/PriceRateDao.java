package com.bnp.parking.dao;

import com.bnp.parking.model.PriceRate;
import com.bnp.parking.model.Status;
import com.bnp.parking.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRateDao extends JpaRepository<PriceRate,Long> {
   PriceRate findByStatusAndVehicleType(Status staus, VehicleType vehicleType);
   PriceRate findByPriceRateId(Long priceRateId);

}
