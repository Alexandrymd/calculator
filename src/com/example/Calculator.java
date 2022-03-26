package com.example;

import com.example.data.Token;
import com.example.service.CalculatorService;
import com.example.util.CalculatorUtil;

import java.math.BigDecimal;
import java.util.List;

public class Calculator {
    public BigDecimal calculate(final String expression) {
        final CalculatorUtil calculatorUtil = new CalculatorUtil();
        final CalculatorService calculatorService = new CalculatorService();

        final List<Token> tokenizedExpression = calculatorUtil.tokenizeExpression(expression);
        return calculatorService.calculateTokenizedExpression(tokenizedExpression);
    }
}
