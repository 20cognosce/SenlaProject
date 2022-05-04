package task5.service.impl;

import task5.build.DI.Autowired;
import task5.build.factory.Component;
import task5.dao.AbstractDao;
import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;
import task5.dao.entity.AbstractEntity;
import task5.service.AbstractService;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public abstract class AbstractServiceImpl<T extends AbstractEntity, D extends AbstractDao<T>> implements AbstractService<T> {
    @Autowired
    protected D currentDao;
    @Autowired
    protected GuestDao guestDao;
    @Autowired
    protected RoomDao roomDao;
    @Autowired
    protected MaintenanceDao maintenanceDao;
    @SuppressWarnings("FieldCanBeLocal")
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
