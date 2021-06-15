package com.bank.api.domain.dao;

import com.bank.api.domain.dto.Card;

import java.util.Set;

public interface CardRepository extends CRUDRepository<Card>{
    Set<Card> getAllByAccountId(long accountId);
    Set<Card> getAll();
    Card get(String cardNumber);
}
