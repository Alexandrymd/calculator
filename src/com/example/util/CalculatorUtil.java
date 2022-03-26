package com.example.util;

import com.example.data.Token;
import com.example.data.TokenNumber;
import com.example.data.TokenType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalculatorUtil {

    public List<Token> tokenizeExpression(final String expression) {
        final List<Token> tokenizedExpression = new ArrayList<>();

        int tokenBeginPosition = 0;

        while (tokenBeginPosition < expression.length()) {

            final int tokenLength = determineTokenLength(tokenBeginPosition, expression);

            final Token token = parseToken(expression.substring(tokenBeginPosition, tokenBeginPosition+tokenLength));

            tokenizedExpression.add(token);

            tokenBeginPosition = tokenBeginPosition + tokenLength;
        }

        return tokenizedExpression;
    }

    private Token parseToken(String tokenToParse) {
        if (tokenToParse.equals("(")) {
            return new Token(TokenType.LEFT_PARENTHESIS);
        }

        if (tokenToParse.equals(")")) {
            return new Token(TokenType.RIGHT_PARENTHESIS);
        }

        if ( tokenToParse.equals("/")) {
            return new Token(TokenType.DIVIDE );
        }

        if ( tokenToParse.equals("*")) {
            return new Token(TokenType.MULTIPLY );
        }

        if ( tokenToParse.equals("+")) {
            return new Token(TokenType.PLUS );
        }

        if ( tokenToParse.equals("-")) {
            return new Token(TokenType.MINUS );
        }

        final BigDecimal number = new BigDecimal(tokenToParse);

        return new TokenNumber(TokenType.NUMBER, number);
    }

    private int determineTokenLength(final int tokenBeginPosition, final String expression) {

        int tokenLength = 1;

            String currentToken = expression.substring(tokenBeginPosition, tokenBeginPosition+tokenLength);

            if (currentToken.equals("-") && characterBeforeTokenBeginIsNotNumber(tokenBeginPosition, expression)) {
                tokenLength = tokenLength + 1;
            }

            currentToken = expression.substring(tokenBeginPosition, tokenBeginPosition+tokenLength);


            if (currentTokenIsANumber(currentToken)) {

                int currentCharacterPosition = tokenBeginPosition+tokenLength-1;

                while (nextCharacterIsDotOrDigit(currentCharacterPosition, expression)) {
                    tokenLength = tokenLength +1;
                    currentCharacterPosition = tokenBeginPosition+tokenLength-1;
                }
            }

        return tokenLength;
    }

    private boolean nextCharacterIsDotOrDigit(final int currentCharacterPosition, final String expression) {
        if (currentCharacterPosition >= expression.length() -1) {
            return false;
        }

        final char nextCharacter = expression.charAt(currentCharacterPosition + 1);

        return Character.isDigit(nextCharacter) || nextCharacter == '.';

    }

    private boolean currentTokenIsANumber(String currentToken) {

       try {
           final BigDecimal number = new BigDecimal(currentToken);
       }
       catch (Exception exception) {
           return false;
       }

       return true;
    }

    private boolean characterBeforeTokenBeginIsNotNumber(int tokenBeginPosition, String expression) {
        if (tokenBeginPosition == 0) {
            return true;
        }

        final char characterBeforeTokenBegin = expression.charAt(tokenBeginPosition-1);

        return !Character.isDigit(characterBeforeTokenBegin);
    }
}
