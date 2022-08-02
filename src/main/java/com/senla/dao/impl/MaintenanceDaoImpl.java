package com.senla.dao.impl;


import com.senla.dao.MaintenanceDao;
import com.senla.model.Guest;
import com.senla.model.Guest2Maintenance;
import com.senla.model.Guest_;
import com.senla.model.Maintenance;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MaintenanceDaoImpl extends AbstractDaoImpl<Maintenance> implements MaintenanceDao {

    @Override
    public void addOrderedMaintenance(Long guestId, Long maintenanceId) {
        Guest2Maintenance guest2Maintenance = new Guest2Maintenance(guestId, maintenanceId, LocalDateTime.now());
        entityManager.persist(guest2Maintenance);
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuestSorted(Long guestId, String fieldToSortBy, String order) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Maintenance> maintenanceCQ = cb.createQuery(Maintenance.class);

        Root<Guest> guestRoot = maintenanceCQ.from(Guest.class);
        ListJoin<Guest, Maintenance> guestMaintenanceListJoin = guestRoot.join(Guest_.orderedMaintenances);
        List<Order> orderList = getOrderList(order, fieldToSortBy, cb, guestRoot);
        return entityManager.createQuery(maintenanceCQ
                        .select(guestMaintenanceListJoin)
                        .where(cb.equal(guestRoot.get(Guest_.id), guestId))
                        .orderBy(orderList)
                ).getResultList();
    }

    @Override
    protected Class<Maintenance> daoEntityClass() {
        return Maintenance.class;
    }
}
