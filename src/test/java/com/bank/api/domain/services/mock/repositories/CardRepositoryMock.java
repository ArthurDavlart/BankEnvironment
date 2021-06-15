package com.bank.api.domain.services.mock.repositories;

import com.bank.api.domain.dao.CardRepository;
import com.bank.api.domain.dto.Card;

import java.util.List;
import java.util.Set;

public class CardRepositoryMock implements CardRepository {
    private List<Card> cards;

    public CardRepositoryMock(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public void creat(Card card) {
        cards.add(card);
    }

    @Override
    public void update(Card card) {

    }

    @Override
    public Card read(long id) {
        return null;
    }

    @Override
    public void delete(Card card) {

    }

    @Override
    public void begin() {

    }

    @Override
    public void commit() {

    }

    @Override
    public Set<Card> getAllByAccountId(long accountId) {
        return null;
    }

    @Override
    public Set<Card> getAll() {
        return null;
    }

    @Override
    public Card get(String cardNumber) {
        return null;
    }
}
