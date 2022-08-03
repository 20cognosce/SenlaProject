package com.senla.dao;

import com.senla.model.Token;

public interface TokenDao extends AbstractDao<Token> {

    Token getTokenByValue(String value);
}