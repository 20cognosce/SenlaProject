package com.senla.javacourse.service.impl;

import com.senla.javacourse.dao.AbstractDao;
import com.senla.javacourse.dao.entity.AbstractEntity;
import com.senla.javacourse.service.AbstractService;
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
