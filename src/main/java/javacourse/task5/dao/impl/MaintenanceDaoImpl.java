package javacourse.task5.dao.impl;

import javacourse.task5.build.factory.Component;
import javacourse.task5.build.orm.OrmManagementUtil;
import javacourse.task5.dao.MaintenanceDao;
import javacourse.task5.dao.entity.Guest;
import javacourse.task5.dao.entity.Maintenance;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MaintenanceDaoImpl extends AbstractDaoImpl<Maintenance> implements MaintenanceDao {
    private static final Logger logger = LoggerFactory.getLogger(MaintenanceDaoImpl.class);
    public MaintenanceDaoImpl() {
        super();
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuest(Guest guest) {
        return guest.getOrderedMaintenances();
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuest(Guest guest, Comparator<Maintenance> comparator) {
        return guest.getOrderedMaintenances().stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public void updateMaintenancePrice(long maintenanceId, int price) {
        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            session.beginTransaction();
            Maintenance maintenance = getById(maintenanceId);
            maintenance.setPrice(price);
            session.update(maintenance);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void addGuestMaintenance(Maintenance maintenanceInstance) {
        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            session.beginTransaction();
            addToRepo(maintenanceInstance);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public String exportData(Maintenance maintenance) {
        return maintenance.getId() + "," +
                maintenance.getName() + "," +
                maintenance.getPrice() + "," +
                maintenance.getCategory();
    }

    @Override
    public Maintenance getDaoEntity() {
        return new Maintenance();
    }

    @Override
    public List<Maintenance> getAll() {
        List<Maintenance> resultList;

        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Maintenance> criteria = cb.createQuery(Maintenance.class);
            Root<Maintenance> root = criteria.from(Maintenance.class);

            Predicate predicateForGuestId = cb.isNull(root.get("guestId"));
            Predicate predicateForTimeStamp = cb.isNull(root.get("orderTime"));
            Predicate predicate = cb.and(predicateForGuestId, predicateForTimeStamp);

            criteria.select(root).where(predicate);

            resultList = session.createQuery(criteria).getResultList();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return resultList;
    }
}
