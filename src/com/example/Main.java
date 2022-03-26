package com.example;

public class Main {

    public static void main(String[] args) {
        final Calculator calculator = new Calculator();
        final String expression = "-6+77*((1-82.98)/(-7.9))";

        System.out.println(calculator.calculate(expression));
    }
}
