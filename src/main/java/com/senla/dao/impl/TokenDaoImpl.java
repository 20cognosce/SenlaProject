package com.senla.dao.impl;

import com.senla.dao.TokenDao;
import com.senla.model.Token;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TokenDaoImpl extends AbstractDaoImpl<Token> implements TokenDao {

    @Override
    public Optional<Token> getTokenByValue(String value) {
        return Optional.empty();
    }

    @Override
    protected Class<Token> daoEntityClass() {
        return Token.class;
    }
}
