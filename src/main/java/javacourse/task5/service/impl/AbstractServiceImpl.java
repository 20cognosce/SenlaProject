package javacourse.task5.service.impl;

import javacourse.task5.build.DI.Autowired;
import javacourse.task5.build.factory.Component;
import javacourse.task5.build.orm.OrmManagementUtil;
import javacourse.task5.dao.AbstractDao;
import javacourse.task5.dao.GuestDao;
import javacourse.task5.dao.MaintenanceDao;
import javacourse.task5.dao.RoomDao;
import javacourse.task5.dao.entity.AbstractEntity;
import javacourse.task5.dao.impl.GuestDaoImpl;
import javacourse.task5.dao.impl.MaintenanceDaoImpl;
import javacourse.task5.dao.impl.RoomDaoImpl;
import javacourse.task5.service.AbstractService;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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
    public void addAll(List<T> list) {
        getDefaultDao().openSessionAndExecuteTransactionTask((session, builder) -> list.forEach(session::save));
    }
}
