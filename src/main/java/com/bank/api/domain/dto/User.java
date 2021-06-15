package com.bank.api.domain.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {
    private long id;
    private String firstName;
    private String secondName;
    private String middleName;
    private Passport passport;
    Set<Account> accounts  = new HashSet<>();

    public User() {
    }

    public User(long id, String firstName, String secondName, String middleName, Passport passport) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.passport = passport;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(firstName, user.firstName) && Objects.equals(secondName, user.secondName) && Objects.equals(middleName, user.middleName) && Objects.equals(passport, user.passport) && Objects.equals(accounts, user.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, middleName, passport, accounts);
    }
}
