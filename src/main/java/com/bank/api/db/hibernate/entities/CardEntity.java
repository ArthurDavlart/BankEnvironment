package com.bank.api.db.hibernate.entities;

import com.bank.api.domain.dto.Card;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CardEntity extends Card {
    private long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public long getId() {
        return id;
    }
}
