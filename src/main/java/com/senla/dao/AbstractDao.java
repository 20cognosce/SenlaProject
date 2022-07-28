package com.senla.dao;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public interface AbstractDao<T> {

    void create(T element) throws KeyAlreadyExistsException;
    void delete(T element) throws NoSuchElementException;
    void update(T element);
    T getById(long id);
    List<T> getAll(String fieldToSortBy, String order);
    List<LocalDateTime> getGuest2MaintenanceOrderTime(long guestId, long maintenanceId);
    List<Order> getOrderList(String ascDesc, String fieldToSortBy, CriteriaBuilder cb, Root<?> root);
}
