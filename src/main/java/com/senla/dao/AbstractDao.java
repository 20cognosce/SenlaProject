package com.senla.dao;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;

public interface AbstractDao<T> {
    void create(T element) throws KeyAlreadyExistsException;
    void delete(T element) throws NoSuchElementException;
    void update(T element);
    T getById(long id);
    List<T> getAll(String fieldToSortBy);
    String exportData(T element);
}
