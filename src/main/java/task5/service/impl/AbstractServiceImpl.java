package task5.service.impl;

import task5.config.DI.DependencyDaoAutowired;
import task5.dao.AbstractDao;
import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;
import task5.dao.entity.AbstractEntity;
import task5.service.AbstractService;

import java.util.List;
import java.util.NoSuchElementException;

public abstract class AbstractServiceImpl<T extends AbstractEntity, D extends AbstractDao<T>> implements AbstractService<T> {
    @DependencyDaoAutowired()
    protected D currentDao;
    @DependencyDaoAutowired(daoClass = GuestDao.class)
    protected GuestDao guestDao;
    @DependencyDaoAutowired(daoClass = RoomDao.class)
    protected RoomDao roomDao;
    @DependencyDaoAutowired(daoClass = MaintenanceDao.class)
    protected MaintenanceDao maintenanceDao;
    private final Class<D> typeParameterClass;

    AbstractServiceImpl(Class<D> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    @Override
    public T getById(long id) throws NoSuchElementException {
        return currentDao.getById(id);
    }

    @Override
    public T getByName(String name) throws NoSuchElementException {
        return currentDao.getByName(name);
    }

    @Override
    public List<T> getAll() {
        return currentDao.getAll();
    }

    @Override
    public void addAll(List<T> list) {
        list.forEach(e -> currentDao.addToRepo(e));
    }
}
