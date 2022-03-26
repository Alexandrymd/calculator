package com.example.data;

import java.math.BigDecimal;

public class TokenNumber extends Token {
    private BigDecimal number;

    public TokenNumber(TokenType tokenType, BigDecimal number) {
        super(tokenType);
        this.number = number;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }
}
