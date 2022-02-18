package com.bnp.parking.common;

import com.bnp.parking.model.VehicleType;

import java.util.Random;

public class Utils {
    public static String generateLetters(int count) {
        String letters = "";
        int n = 'Z' - 'A' + 1;
        for (int i = 0; i < count; i++) {
            char c = (char) ('A' + Math.random() * n);
            letters += c;
        }
        return letters;
    }

    public static String generateDigits(int count) {
        String digits = "";
        int n = '9' - '1' + 1;
        for (int i = 0; i < count; i++) {
            char c = (char) ('1' + Math.random() * n);
            digits += c;
        }
        return digits;
    }

    public static String generateRandomCarNumber() {
        return generateDigits(2) + "-" + generateLetters(1) + "-" + generateDigits(3);
    }

    public static String generateRandomPan() {
        return "61043379" + generateDigits(8);
    }

    public static boolean getRandomPaymentStatus() {
        Random random = new Random();
        return random.nextBoolean();
    }

    public static VehicleType getRandomVehicleType() {
        Random random = new Random();
        if (random.nextBoolean())
            return VehicleType.PublicCar;
        else
            return VehicleType.CommercialCar;
    }
}
