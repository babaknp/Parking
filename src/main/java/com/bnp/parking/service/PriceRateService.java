package com.bnp.parking.service;

import com.bnp.parking.dao.PriceRateDao;
import com.bnp.parking.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;

@Service
public class PriceRateService {
    @Autowired
    PriceRateDao priceRateDao;

    @Transactional
    public ResponseEntity<PriceRate> addPriceRate(PriceRate priceRate) throws Exception {
        try {
            PriceRate priceRateObj = priceRateDao.findByStatusAndVehicleType(priceRate.getStatus(), priceRate.getVehicleType());
            if (priceRateObj == null) {
                priceRate.setInsertDate(LocalDateTime.now());
                priceRateDao.save(priceRate);
                return new ResponseEntity<PriceRate>(priceRate, HttpStatus.OK);
            } else {
                return new ResponseEntity<PriceRate>(HttpStatus.valueOf(GeneralResponse.PRICE_RATE_IS_EXIST));
            }
        } catch (Exception e) {
            return new ResponseEntity<PriceRate>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
