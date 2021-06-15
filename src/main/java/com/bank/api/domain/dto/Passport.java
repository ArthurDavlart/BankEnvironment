package com.bank.api.domain.dto;

import java.util.Objects;

public class Passport {
    private int serial;
    private int number;
    private PassportType passportType;

    public Passport() {
    }

    public Passport(int serial, int number, PassportType passportType) {
        this.serial = serial;
        this.number = number;
        this.passportType = passportType;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public PassportType getPassportType() {
        return passportType;
    }

    public void setPassportType(PassportType passportType) {
        this.passportType = passportType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return serial == passport.serial && number == passport.number && passportType == passport.passportType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serial, number, passportType);
    }
}
