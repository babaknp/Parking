package com.bnp.parking.service;

import com.bnp.parking.common.Utils;
import com.bnp.parking.dao.ParkingInfoDao;
import com.bnp.parking.dao.PaymentDao;
import com.bnp.parking.dao.PriceRateDao;
import com.bnp.parking.dao.VehicleDao;
import com.bnp.parking.model.*;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class ParkingServiceImpl {
    @Autowired
    private VehicleDao vehicleDao;
    @Autowired
    private ParkingInfoDao parkingInfoDao;
    @Autowired
    PriceRateDao priceRateDao;
    @Autowired
    PaymentDao paymentDao;



    @Transactional(rollbackOn = ServiceException.class)
    public ResponseEntity<ParkingInfo> entryRegistration(EntryRegistrationInput registrationInput) throws Exception {
        try {
            Vehicle vehicleObj = new Vehicle();
            vehicleObj = vehicleDao.findByCarNumber(registrationInput.getCarNumber());
            if (vehicleObj == null) {
                Vehicle vehicle = new Vehicle(registrationInput.getCarNumber(), registrationInput.getVehicleType());
                vehicleObj = vehicleDao.save(vehicle);
            }
            PriceRate priceRate = priceRateDao.findByStatusAndVehicleType(Status.ACTIVE, registrationInput.getVehicleType());

            ParkingInfo parkingInfo = new ParkingInfo(vehicleObj, priceRate);

            ParkingInfo parkingInfoObj = parkingInfoDao.save(parkingInfo);

            return new ResponseEntity<ParkingInfo>(parkingInfoObj, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ParkingInfo>(HttpStatus.EXPECTATION_FAILED);
        }
    }


    @Transactional(rollbackOn = ServiceException.class)
    public ResponseEntity<ParkingInfo> exitVehicle(String carNumber) throws Exception {
        try {
            Vehicle vehicle = vehicleDao.findByCarNumber(carNumber);

            ParkingInfo parkingInfo = parkingInfoDao.findByVehicleObjAndOutTime(vehicle, null);
            if (parkingInfo != null) {
                PriceRate priceRate = priceRateDao.findByPriceRateId(parkingInfo.getPriceRateObj().getPriceRateId());

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();


                parkingInfo.setTotalAmount(calculateAmountPayment(parkingInfo.getInTime(), dtf.format(now), priceRate));
                Payment payment = new Payment();
                boolean paymentStatus = paymentProcess(parkingInfo, Utils.generateRandomPan(), parkingInfo.getTotalAmount(), payment);
                parkingInfo.setPaymentStatus(paymentStatus ? PaymentStatus.Success : PaymentStatus.UnSuccess);
                parkingInfo.setOutTime(paymentStatus ? dtf.format(now) : null);
                List<Payment> payments = new ArrayList<>();
                payments.add(payment);
                parkingInfo.getPaymentList().add(payment);
                parkingInfoDao.save(parkingInfo);
                return new ResponseEntity<ParkingInfo>(parkingInfo, HttpStatus.OK);
            }else {
                return new ResponseEntity<ParkingInfo>(parkingInfo, HttpStatus.valueOf(GeneralResponse.RECORD_NOT_FOUND));
            }
        } catch (Exception e) {
            return new ResponseEntity<ParkingInfo>(HttpStatus.EXPECTATION_FAILED);
        }
    }


    @Transactional(rollbackOn = ServiceException.class)
    public boolean paymentProcess(ParkingInfo parkingInfo, String pan, Long amount, Payment payment) throws Exception {
        try {
            payment.setTransactionAmount(amount);
            payment.setPan(pan);
            payment.setParkingInfoObj(parkingInfo);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            payment.setInsertDate(dtf.format(now));
            boolean paymentResult = Utils.getRandomPaymentStatus();
            //boolean paymentResult = true;
            payment.setPaymentStatus(paymentResult ? PaymentStatus.Success : PaymentStatus.UnSuccess);
            payment.setTraceNumber(Long.valueOf(Utils.generateDigits(6)));
            payment = paymentDao.save(payment);
            return paymentResult;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public ResponseEntity<List<ParkingInfo>> getVehicleReport(VehicleReportInput vehicleReportInput) {
        try {
            Vehicle vehicle = vehicleDao.findByCarNumber(vehicleReportInput.getCarNumber());
            List<ParkingInfo> parkingInfoList = parkingInfoDao.findByVehicleObjAndCreateDateBetween(vehicle, vehicleReportInput.getFromDate(), vehicleReportInput.getToDate());
            return new ResponseEntity<List<ParkingInfo>>(parkingInfoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<ParkingInfo>>(HttpStatus.EXPECTATION_FAILED);
        }
    }


    public Long calculateAmountPayment(String start_date, String end_date, PriceRate priceRate) throws Exception {
        try {
            String[] startDateStr = start_date.substring(0, 10).split("-");
            String[] endDateStr = end_date.substring(0, 10).split("-");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            LocalDate firstDate = LocalDate.of(Integer.parseInt(startDateStr[0]), Integer.parseInt(startDateStr[1]), Integer.parseInt(startDateStr[2]));
            LocalDate endDate = LocalDate.of(Integer.parseInt(endDateStr[0]), Integer.parseInt(endDateStr[1]), Integer.parseInt(endDateStr[2]));

            Period period = Period.between(firstDate, endDate);
            int difference_In_days = period.getDays();
            int difference_In_months = period.getMonths();
            long difference_In_Time = d2.getTime() - d1.getTime();
            long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
            Long totalAmount = priceRate.getEntryPrice() + (priceRate.getHourlyRate() * difference_In_Hours) + (priceRate.getDailyRate() * difference_In_days) + (priceRate.getMonthlyRate() * difference_In_months);
            return totalAmount;
        } catch (Exception e) {
            return 0L;
        }
    }

    public void priceRateConfig() {
        List<PriceRate> list = priceRateDao.findAll();
        if (list.size() <= 0) {
            PriceRate priceRate = new PriceRate();
            priceRate.setVehicleType(VehicleType.PublicCar);
            priceRate.setStatus(Status.ACTIVE);
            priceRate.setInsertDate(LocalDateTime.now());
            priceRate.setEntryPrice(1000L);
            priceRate.setHourlyRate(2000L);
            priceRate.setDailyRate(4000L);
            priceRate.setMonthlyRate(6000L);
            priceRateDao.save(priceRate);

            priceRate = new PriceRate();
            priceRate.setVehicleType(VehicleType.CommercialCar);
            priceRate.setStatus(Status.ACTIVE);
            priceRate.setInsertDate(LocalDateTime.now());
            priceRate.setEntryPrice(500L);
            priceRate.setHourlyRate(1000L);
            priceRate.setDailyRate(2000L);
            priceRate.setMonthlyRate(3000L);
            priceRateDao.save(priceRate);

        }
    }

}
