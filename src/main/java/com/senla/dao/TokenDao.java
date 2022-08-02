package com.senla.dao;

import com.senla.model.Token;

import java.util.Optional;

public interface TokenDao extends AbstractDao<Token> {
    Optional<Token> getTokenByValue(String value);
}