package com.example.service;

import com.example.data.Token;
import com.example.data.TokenNumber;
import com.example.data.TokenType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CalculatorService {

    public BigDecimal calculateTokenizedExpression(final List<Token> tokenizedExpression) {

        List<Token> expressionBeingResolved = tokenizedExpression;

        while (expressionHasParenthesis(expressionBeingResolved)) {
            int firstClosedParenthesis = findFirstClosedParenthesis(expressionBeingResolved);
            int firstOpenParenthesisLeftOfFirstClosedParenthesis = findFirstOpenParenthesisLeftOfPosition(firstClosedParenthesis, expressionBeingResolved);

            final TokenNumber result = evaluateExpression(expressionBeingResolved.subList(firstOpenParenthesisLeftOfFirstClosedParenthesis+1, firstClosedParenthesis));

            final List<Token> expressionUntilLeftParenthesis = expressionBeingResolved.subList(0, firstOpenParenthesisLeftOfFirstClosedParenthesis);
            final List<Token> expressionFromRightParenthesis = expressionBeingResolved.subList(firstClosedParenthesis + 1, expressionBeingResolved.size());

            final List<Token> expressionWithCalculatedParenthesis = new ArrayList<>();

            expressionWithCalculatedParenthesis.addAll(expressionUntilLeftParenthesis);
            expressionWithCalculatedParenthesis.add(result);
            expressionWithCalculatedParenthesis.addAll(expressionFromRightParenthesis);

            expressionBeingResolved = expressionWithCalculatedParenthesis;
        }



        return evaluateExpression(expressionBeingResolved).getNumber();
    }

    private TokenNumber evaluateExpression(final List<Token> expression) {

        List<Token> expressionBeingCalculated = expression;

        while (multiplyExists(expressionBeingCalculated)) {
            int positionOfOperation = findFirstMultiply(expressionBeingCalculated);

            final BigDecimal leftNumber = ((TokenNumber)expressionBeingCalculated.get(positionOfOperation-1)).getNumber();
            final BigDecimal rightNumber = ((TokenNumber)expressionBeingCalculated.get(positionOfOperation+1)).getNumber();

            final BigDecimal result = leftNumber.multiply(rightNumber);

            final List<Token> expressionUntilFirstMultipliedNumber = expressionBeingCalculated.subList(0, positionOfOperation - 1);
            final List<Token> expressionAfterSecondMultipliedNumber = expressionBeingCalculated.subList(positionOfOperation + 2, expressionBeingCalculated.size());


            final List<Token> expressionAfterCalculation = new ArrayList<>();
            expressionAfterCalculation.addAll(expressionUntilFirstMultipliedNumber);
            expressionAfterCalculation.add(new TokenNumber(TokenType.NUMBER, result));
            expressionAfterCalculation.addAll(expressionAfterSecondMultipliedNumber);

            expressionBeingCalculated = expressionAfterCalculation;
        }

        while (divideExists(expressionBeingCalculated)) {
            int positionOfOperation = findFirstDivide(expressionBeingCalculated);

            final BigDecimal leftNumber = ((TokenNumber)expressionBeingCalculated.get(positionOfOperation-1)).getNumber();
            final BigDecimal rightNumber = ((TokenNumber)expressionBeingCalculated.get(positionOfOperation+1)).getNumber();

            final BigDecimal result = leftNumber.divide(rightNumber, 10, RoundingMode.HALF_UP);

            final List<Token> expressionUntilFirstMultipliedNumber = expressionBeingCalculated.subList(0, positionOfOperation - 1);
            final List<Token> expressionAfterSecondMultipliedNumber = expressionBeingCalculated.subList(positionOfOperation + 2, expressionBeingCalculated.size());


            final List<Token> expressionAfterCalculation = new ArrayList<>();
            expressionAfterCalculation.addAll(expressionUntilFirstMultipliedNumber);
            expressionAfterCalculation.add(new TokenNumber(TokenType.NUMBER, result));
            expressionAfterCalculation.addAll(expressionAfterSecondMultipliedNumber);

            expressionBeingCalculated = expressionAfterCalculation;
        }

        while (plusExists(expressionBeingCalculated)) {
            int positionOfOperation = findFirstPlus(expressionBeingCalculated);

            final BigDecimal leftNumber = ((TokenNumber)expressionBeingCalculated.get(positionOfOperation-1)).getNumber();
            final BigDecimal rightNumber = ((TokenNumber)expressionBeingCalculated.get(positionOfOperation+1)).getNumber();

            final BigDecimal result = leftNumber.add(rightNumber);

            final List<Token> expressionUntilFirstMultipliedNumber = expressionBeingCalculated.subList(0, positionOfOperation - 1);
            final List<Token> expressionAfterSecondMultipliedNumber = expressionBeingCalculated.subList(positionOfOperation + 2, expressionBeingCalculated.size());


            final List<Token> expressionAfterCalculation = new ArrayList<>();
            expressionAfterCalculation.addAll(expressionUntilFirstMultipliedNumber);
            expressionAfterCalculation.add(new TokenNumber(TokenType.NUMBER, result));
            expressionAfterCalculation.addAll(expressionAfterSecondMultipliedNumber);

            expressionBeingCalculated = expressionAfterCalculation;
        }

        while (minusExists(expressionBeingCalculated)) {
            int positionOfOperation = findFirstMinus(expressionBeingCalculated);

            final BigDecimal leftNumber = ((TokenNumber)expressionBeingCalculated.get(positionOfOperation-1)).getNumber();
            final BigDecimal rightNumber = ((TokenNumber)expressionBeingCalculated.get(positionOfOperation+1)).getNumber();

            final BigDecimal result = leftNumber.subtract(rightNumber);

            final List<Token> expressionUntilFirstMultipliedNumber = expressionBeingCalculated.subList(0, positionOfOperation - 1);
            final List<Token> expressionAfterSecondMultipliedNumber = expressionBeingCalculated.subList(positionOfOperation + 2, expressionBeingCalculated.size());


            final List<Token> expressionAfterCalculation = new ArrayList<>();
            expressionAfterCalculation.addAll(expressionUntilFirstMultipliedNumber);
            expressionAfterCalculation.add(new TokenNumber(TokenType.NUMBER, result));
            expressionAfterCalculation.addAll(expressionAfterSecondMultipliedNumber);

            expressionBeingCalculated = expressionAfterCalculation;
        }

        return (TokenNumber) expressionBeingCalculated.get(0);
    }

    private int findFirstMultiply(List<Token> expressionBeingCalculated) {
        for (int i = 0; i < expressionBeingCalculated.size(); i++) {
            if (expressionBeingCalculated.get(i).getTokenType().equals(TokenType.MULTIPLY)) {
                return i;
            }
        }

        throw new RuntimeException("Expected open parenthesis but found none");
    }

    private boolean multiplyExists(List<Token> expressionBeingCalculated) {
        for (final Token token : expressionBeingCalculated) {
            if (token.getTokenType().equals(TokenType.MULTIPLY)) {
                return true;
            }
        }

        return false;
    }

    private int findFirstDivide(List<Token> expressionBeingCalculated) {
        for (int i = 0; i < expressionBeingCalculated.size(); i++) {
            if (expressionBeingCalculated.get(i).getTokenType().equals(TokenType.DIVIDE)) {
                return i;
            }
        }

        throw new RuntimeException("Expected divide but found none");
    }

    private boolean divideExists(List<Token> expressionBeingCalculated) {
        for (final Token token : expressionBeingCalculated) {
            if (token.getTokenType().equals(TokenType.DIVIDE)) {
                return true;
            }
        }

        return false;
    }

    private int findFirstPlus(List<Token> expressionBeingCalculated) {
        for (int i = 0; i < expressionBeingCalculated.size(); i++) {
            if (expressionBeingCalculated.get(i).getTokenType().equals(TokenType.PLUS)) {
                return i;
            }
        }

        throw new RuntimeException("Expected plus but found none");
    }

    private boolean plusExists(List<Token> expressionBeingCalculated) {
        for (final Token token : expressionBeingCalculated) {
            if (token.getTokenType().equals(TokenType.PLUS)) {
                return true;
            }
        }

        return false;
    }

    private int findFirstMinus(List<Token> expressionBeingCalculated) {
        for (int i = 0; i < expressionBeingCalculated.size(); i++) {
            if (expressionBeingCalculated.get(i).getTokenType().equals(TokenType.MINUS)) {
                return i;
            }
        }

        throw new RuntimeException("Expected minus but found none");
    }

    private boolean minusExists(List<Token> expressionBeingCalculated) {
        for (final Token token : expressionBeingCalculated) {
            if (token.getTokenType().equals(TokenType.MINUS)) {
                return true;
            }
        }

        return false;
    }

    private int findFirstOpenParenthesisLeftOfPosition(int firstClosedParenthesis, List<Token> tokenList) {
        for (int i = firstClosedParenthesis; i >= 0; i--) {
            if (tokenList.get(i).getTokenType().equals(TokenType.LEFT_PARENTHESIS)) {
                return i;
            }
        }

        throw new RuntimeException("Expected open parenthesis but found none");
    }

    private int findFirstClosedParenthesis(List<Token> tokenList) {
        for (int i=0; i<tokenList.size(); i++) {
            if (tokenList.get(i).getTokenType().equals(TokenType.RIGHT_PARENTHESIS)) {
                return i;
            }
        }

        throw new RuntimeException("Expected closed parenthesis but found none");
    }

    private boolean expressionHasParenthesis(List<Token> expressionBeingResolved) {
        for (final Token token : expressionBeingResolved) {
            if (token.getTokenType().equals(TokenType.LEFT_PARENTHESIS)) {
                return true;
            }
        }

        return false;
    }
}
