package com.bank.api.domain.dto;

import java.math.BigDecimal;

public class Deposit {
    private Currency currency;
    private BigDecimal quantity;

    public Deposit() {
    }

    public Deposit(BigDecimal quantity, Currency currency) {
        this.currency = currency;
        this.quantity = quantity;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
