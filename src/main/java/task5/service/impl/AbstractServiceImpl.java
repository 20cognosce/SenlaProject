package task5.service.impl;

import task5.build.DI.Autowired;
import task5.build.factory.Component;
import task5.dao.AbstractDao;
import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;
import task5.dao.entity.AbstractEntity;
import task5.dao.impl.GuestDaoImpl;
import task5.dao.impl.MaintenanceDaoImpl;
import task5.dao.impl.RoomDaoImpl;
import task5.service.AbstractService;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public abstract class AbstractServiceImpl<T extends AbstractEntity, D extends AbstractDao<T>> implements AbstractService<T, D> {
    @Autowired
    protected GuestDao guestDao = new GuestDaoImpl();
    @Autowired
    protected RoomDao roomDao = new RoomDaoImpl();
    @Autowired
    protected MaintenanceDao maintenanceDao = new MaintenanceDaoImpl();

    protected abstract D getDefaultDao();

    @Override
    public T getById(long id) throws NoSuchElementException {
        return getDefaultDao().getById(id);
    }

    @Override
    public T getByName(String name) throws NoSuchElementException {
        return getDefaultDao().getByName(name);
    }

    @Override
    public List<T> getAll() {
        return getDefaultDao().getAll();
    }

    @Override
    public void addAll(List<T> list) {
        list.forEach(e -> getDefaultDao().addToRepo(e));
    }
}
