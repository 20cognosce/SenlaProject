package com.senla.service.impl;

import com.senla.dao.AbstractDao;
import com.senla.model.AbstractEntity;
import com.senla.service.AbstractService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

public abstract class AbstractServiceImpl<T extends AbstractEntity, D extends AbstractDao<T>> implements AbstractService<T, D> {
    protected abstract D getDefaultDao();

    @Override
    public T getById(long id) throws NoSuchElementException {
        return getDefaultDao().getById(id);
    }

    @Override
    @Transactional
    public void addAll(List<T> list) {
        list.forEach(e -> getDefaultDao().create(e));
    }

    @Override
    public List<T> getAll() {
        return getDefaultDao().getAll("id");
    }
 }
