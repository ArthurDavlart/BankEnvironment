package com.bank.api.domain.dto;

import java.util.Objects;

public class Card {
    protected long id;
    protected String number;
    protected long accountId;

    public Card() {
    }

    public Card(String number, long accountId) {
        this.number = number;
        this.accountId = accountId;
    }

    public Card(long id, String number, long accountId) {
        this.id = id;
        this.number = number;
        this.accountId = accountId;
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

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id && accountId == card.accountId && Objects.equals(number, card.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, accountId);
    }
}
