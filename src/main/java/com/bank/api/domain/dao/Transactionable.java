package com.bank.api.domain.dao;

public interface Transactionable {
    void begin();
    void commit();
}
