package com.senla.javacourse.service.impl;

import com.senla.javacourse.dao.AbstractDao;
import com.senla.javacourse.dao.GuestDao;
import com.senla.javacourse.dao.MaintenanceDao;
import com.senla.javacourse.dao.RoomDao;
import com.senla.javacourse.dao.entity.AbstractEntity;
import com.senla.javacourse.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

public abstract class AbstractServiceImpl<T extends AbstractEntity, D extends AbstractDao<T>> implements AbstractService<T, D> {
    @Autowired //TODO: а как без autowired?
    protected GuestDao guestDao;
    @Autowired
    protected RoomDao roomDao;
    @Autowired
    protected MaintenanceDao maintenanceDao;

    public AbstractServiceImpl(GuestDao guestDao, RoomDao roomDao, MaintenanceDao maintenanceDao) {
        this.guestDao = guestDao;
        this.roomDao = roomDao;
        this.maintenanceDao = maintenanceDao;
    }

    public AbstractServiceImpl() {
    }

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
 }
