package com.senla.dao.impl;

import com.senla.dao.AbstractDao;
import com.senla.model.AbstractEntity;
import com.senla.model.Maintenance;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public abstract class AbstractDaoImpl<T extends AbstractEntity> implements AbstractDao<T> {
    @PersistenceContext
    protected EntityManager entityManager;

    protected abstract Class<T> daoEntityClass();

    @Override
    public void create(T element) {
        entityManager.persist(element);
    }

    @Override
    public void delete(T element) {
        entityManager.remove(element);
    }

    @Override
    public void update(T element) {
        entityManager.merge(element);
    }

    @Override
    public List<T> getAll(String fieldToSortBy) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select e from ").append(daoEntityClass().getName()).append(" e");
        if (daoEntityClass().isAssignableFrom(Maintenance.class)) {
            stringBuilder.append(" where e.guest = null and e.orderTime = null");
            //в maintenance я храню и шаблоны услуг и их инстансы
        }
        stringBuilder.append(" order by ").append(fieldToSortBy).append(" asc");

        TypedQuery<T> q = entityManager.createQuery(stringBuilder.toString(), daoEntityClass());
        return q.getResultList();
    }

    @Override
    public T getById(long id) throws NoSuchElementException {
        T entity = entityManager.find(daoEntityClass(), id);
        if (Objects.isNull(entity)) {
            throw new NoSuchElementException("Entity not found");
        } else {
            return entity;
        }
    }
}
