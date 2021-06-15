package com.bank.api.domain.services.mock.repositories;

import com.bank.api.domain.dao.AccountRepository;
import com.bank.api.domain.dto.Account;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class AccountRepositoryMock implements AccountRepository {
    private List<Account> accounts;

    public AccountRepositoryMock(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public void creat(Account account) {
        accounts.add(account);
    }

    @Override
    public void update(Account account) {
        int accountIndex = IntStream.range(0, accounts.size())
                .filter(i -> accounts.get(i).getId() == account.getId())
                .findFirst().getAsInt();

        accounts.set(accountIndex, account);
    }

    @Override
    public Account read(long id) {
        return accounts.stream()
                .filter(account -> account.getId() == id).findFirst().get();
    }

    @Override
    public void delete(Account account) {

    }

    @Override
    public Account get(String accountNumber) {
        return accounts.stream()
                .filter(account -> account.getNumber().equals(accountNumber))
                .findAny().orElse(null);
    }

    @Override
    public Set<Account> getAll() {
        return null;
    }

    @Override
    public Set<Account> getAllByUserId(long userId) {
        return null;
    }

    @Override
    public void begin() {

    }

    @Override
    public void commit() {

    }
}
