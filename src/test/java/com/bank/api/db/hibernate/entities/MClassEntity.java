package com.bank.api.db.hibernate.entities;

import com.bank.api.db.hibernate.classes.MClass;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "mclass_entity")
public class MClassEntity extends MClass {
    @Id
    @GeneratedValue
    private long id;
    private int intField;

    @Override
    public int getIntField() {
        return intField;
    }

    @Override
    public void setIntField(int intField) {
        this.intField = intField;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MClassEntity that = (MClassEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
