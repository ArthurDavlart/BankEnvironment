package com.bank.api.domain.dao;

public interface CRUDRepository<T> extends Transactionable{
    void creat(T t);
    void update(T t);
    T read(long id);
    void delete(T t);
}
