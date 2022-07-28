package com.senla.service.impl;

import com.senla.build.config.SortEnum;
import com.senla.dao.GuestDao;
import com.senla.dao.MaintenanceDao;
import com.senla.model.Guest;
import com.senla.model.Guest_;
import com.senla.model.Maintenance;
import com.senla.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl extends AbstractServiceImpl<Maintenance, MaintenanceDao> implements MaintenanceService {

    private final MaintenanceDao maintenanceDao;
    private final GuestDao guestDao;

    @Transactional
    @Override
    public void createMaintenance(Maintenance maintenance) {
        maintenanceDao.create(maintenance);
    }

    @Transactional
    @Override
    public void executeMaintenance(long guestId, long maintenanceId) {
        Guest guest = guestDao.getById(guestId);
        Maintenance maintenance = maintenanceDao.getById(maintenanceId);
        maintenanceDao.addOrderedMaintenance(guestId, maintenanceId);
        guestDao.updateGuestPrice(guest, (guest.getPrice() + maintenance.getPrice()));
    }

    @Override
    @Transactional
    public void updateMaintenance(Maintenance maintenance) {
        maintenanceDao.update(maintenance);
    }

    public List<Maintenance> getAllMaintenancesSorted(SortEnum sortEnum, String order) {
        String fieldToSort = getFieldToSortFromEnum(sortEnum);
        return getAll(fieldToSort, order);
    }

    public List<Maintenance> getMaintenancesOfGuestSorted(Long guestId, SortEnum sortEnum, String order) {
        List<Maintenance> resultList1 = getMaintenancesOfGuestSorted1(guestId, sortEnum, order);
        List<Maintenance> resultList2 = getMaintenancesOfGuestSorted2(guestId, sortEnum, order);
        return resultList1;
    }

    public List<Maintenance> getMaintenancesOfGuestSorted1(Long guestId, SortEnum sortEnum, String order) {
        String fieldToSort = getFieldToSortFromEnum(sortEnum);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Maintenance> maintenanceCQ = cb.createQuery(Maintenance.class);
        Root<Maintenance> root = maintenanceCQ.from(Maintenance.class);
        Root<Guest> guestRoot = maintenanceCQ.from(Guest.class);
        root.fetch("guests", JoinType.LEFT);
        List<Order> orderList = getDefaultDao().getOrderList(order, fieldToSort, cb, root);

        TypedQuery<Maintenance> query = entityManager.createQuery(maintenanceCQ
                .select(root)
                .where(cb.equal(guestRoot.get(Guest_.id), guestId))
                .orderBy(orderList)
                .distinct(true)
        );
        return query.getResultList();
    }

    public List<Maintenance> getMaintenancesOfGuestSorted2(Long guestId, SortEnum sortEnum, String order) {
        String fieldToSort = getFieldToSortFromEnum(sortEnum);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Maintenance> maintenanceCQ = cb.createQuery(Maintenance.class);
        Root<Guest> guestRootFromMaintenanceCQ = maintenanceCQ.from(Guest.class);
        List<Order> orderList = getDefaultDao().getOrderList(order, fieldToSort, cb, guestRootFromMaintenanceCQ);

        Join<Guest, Maintenance> join = guestRootFromMaintenanceCQ.join(Guest_.orderedMaintenances);
        maintenanceCQ.where(cb.equal(guestRootFromMaintenanceCQ.get(Guest_.id), guestId));

        return entityManager.createQuery(maintenanceCQ
                .select(join)
                .orderBy(orderList)
                ).getResultList();
    }

    @Override
    public List<Maintenance> sortByAddition(String order) {
        return getAllMaintenancesSorted(SortEnum.BY_ADDITION, order);
    }

    @Override
    public List<Maintenance> sortByPrice(String order) {
        return getAllMaintenancesSorted(SortEnum.BY_PRICE, order);
    }

    @Override
    public List<Maintenance> sortByCategory(String order) {
        return getAllMaintenancesSorted(SortEnum.BY_CATEGORY, order);
    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByAddition(long guestId, String order) {
        return getMaintenancesOfGuestSorted(guestId, SortEnum.BY_ADDITION, order);
    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByPrice(long guestId, String order) {
        return getMaintenancesOfGuestSorted(guestId, SortEnum.BY_PRICE, order);
    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByTime(long guestId, String order) {
        return getMaintenancesOfGuestSorted(guestId, SortEnum.BY_TIME, order);
    }

    @Override
    public MaintenanceDao getDefaultDao() {
        return maintenanceDao;
    }
}
