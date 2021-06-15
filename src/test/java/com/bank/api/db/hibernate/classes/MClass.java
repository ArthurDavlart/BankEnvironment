package com.bank.api.db.hibernate.classes;

public class MClass {
    protected long id;
    protected int intField;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIntField() {
        return intField;
    }

    public void setIntField(int intField) {
        this.intField = intField;
    }
}
