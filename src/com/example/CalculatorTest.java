package com.example;

import java.math.BigDecimal;

public class CalculatorTest {

    public static void main(String[] args) {
        final Calculator calculator = new Calculator();

        BigDecimal result1 = calculator.calculate("-6+77*((1-82.98)/(-7.9))");

        if (!result1.toString().equals("793.0455696223")) {
            throw new RuntimeException("Expected result 793.0455696223, got " + result1);
        }

        BigDecimal result2 = calculator.calculate("2+2*2");

        if (!result2.toString().equals("6")) {
            throw new RuntimeException("Expected result 6, got " + result2);
        }


        System.out.println("Tests passed successfully");
    }
}
