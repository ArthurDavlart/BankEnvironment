package com.bank.api.domain.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Account {
    private long id;
    private String number;
    private BigDecimal balance;
    private Currency currency;
    private Set<Card> cards = new HashSet<>();;
    private long userId;

    public Account() {
    }

    public Account(String number, Currency currency, long userId) {
        this.number = number;
        this.userId = userId;
        this.balance = BigDecimal.valueOf(0.0);
        this.currency = currency;
    }

    public Account(String number, long userId) {
        this(number, Currency.RUB, userId);
    }

    public Account(long id, String number,Currency currency, long userId) {
        this(number, Currency.RUB, userId);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && userId == account.userId && Objects.equals(number, account.number) && Objects.equals(balance, account.balance) && currency == account.currency && Objects.equals(cards, account.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, balance, currency, cards, userId);
    }
}
