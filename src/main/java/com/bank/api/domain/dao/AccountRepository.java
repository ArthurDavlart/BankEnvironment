package com.bank.api.domain.dao;

import com.bank.api.domain.dto.Account;

import java.util.List;
import java.util.Set;

public interface AccountRepository extends CRUDRepository<Account> {
    Account get(String accountNumber);
    Set<Account> getAll();
    Set<Account> getAllByUserId(long userId);
}
