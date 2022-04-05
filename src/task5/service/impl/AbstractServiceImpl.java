package task5.service.impl;

import task5.dao.AbstractDao;
import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;
import task5.dao.model.AbstractEntity;
import task5.service.AbstractService;

import java.util.List;
import java.util.NoSuchElementException;

//Stack overflow helped so much to deal with generics
public abstract class AbstractServiceImpl<T extends AbstractEntity, D extends AbstractDao<T>> implements AbstractService<T> {
    protected D currentDao;
    protected GuestDao guestDao;
    protected RoomDao roomDao;
    protected MaintenanceDao maintenanceDao;

    public AbstractServiceImpl (D currentDao, GuestDao guestDao, RoomDao roomDao, MaintenanceDao maintenanceDao) {
        this.guestDao = guestDao;
        this.maintenanceDao = maintenanceDao;
        this.roomDao = roomDao;
        this.currentDao = currentDao;
    }

    static public class GenericClass<D> {
        private final Class<D> type;

        public GenericClass(Class<D> type) {
            this.type = type;
        }

        public Class<D> getMyType() {
            return this.type;
        }
    }

    @Override
    public T getById(int id) throws NoSuchElementException {
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
}
