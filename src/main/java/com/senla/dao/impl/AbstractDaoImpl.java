package com.senla.dao.impl;

import com.senla.dao.AbstractDao;
import com.senla.model.AbstractEntity;
import com.senla.model.Guest2Maintenance;
import com.senla.model.Guest2Maintenance_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<T> getAll(String fieldToSortBy, String order) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(daoEntityClass());
        Root<T> tRoot = cq.from(daoEntityClass());
        List<Order> orderList = getOrderList(order, fieldToSortBy, cb, tRoot);
        TypedQuery<T> query = entityManager.createQuery(cq
                .select(tRoot)
                .orderBy(orderList));

        return query.getResultList();
    }

    @Override
    public List<Guest2Maintenance> getGuest2Maintenance(long guestId, long maintenanceId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Guest2Maintenance> cq = cb.createQuery(Guest2Maintenance.class);
        Root<Guest2Maintenance> root = cq.from(Guest2Maintenance.class);

        return entityManager.createQuery(cq
                        .select(root)
                        .where(cb.and(
                                cb.equal(root.get(Guest2Maintenance_.guestId), guestId),
                                cb.equal(root.get(Guest2Maintenance_.maintenanceId), maintenanceId)))
                ).getResultList();
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

    @Override
    public List<Order> getOrderList(String ascDesc, String fieldToSortBy, CriteriaBuilder cb, Root<?> root) {
        List<Order> orderList = new ArrayList<>();
        if (Objects.equals(ascDesc, "asc")) {
            orderList.add(cb.asc(root.get(fieldToSortBy)));
        }
        if (Objects.equals(ascDesc, "desc")) {
            orderList.add(cb.desc(root.get(fieldToSortBy)));
        }
        return orderList;
    }
}
