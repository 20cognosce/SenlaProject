package com.senla.dao.impl;

import com.senla.dao.TokenDao;
import com.senla.model.Token;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class TokenDaoImpl extends AbstractDaoImpl<Token> implements TokenDao {

    @Override
    public Token getTokenByValue(String value) {
        TypedQuery<Token> q = entityManager.createQuery(
                "select t from Token t where t.value = :value", Token.class
        );
        q.setParameter("value", value);
        return q.getSingleResult();
    }

    @Override
    protected Class<Token> daoEntityClass() {
        return Token.class;
    }
}
