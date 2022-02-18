package com.bnp.parking.controllers;

import com.bnp.parking.model.GeneralResponse;
import com.bnp.parking.model.PriceRate;
import com.bnp.parking.service.PriceRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/priceRate")
public class PriceRateController {

    @Autowired
    private PriceRateService priceRateService;

    @PostMapping(value = "/addPriceRate")
    public ResponseEntity<PriceRate> addPriceRate(@RequestBody PriceRate priceRateObj) throws Exception {
        try {
            return priceRateService.addPriceRate(priceRateObj);
        } catch (Exception e) {
            return new ResponseEntity<PriceRate>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
