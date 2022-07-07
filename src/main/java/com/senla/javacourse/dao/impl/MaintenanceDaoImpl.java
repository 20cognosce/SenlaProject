package com.senla.javacourse.dao.impl;


import com.senla.javacourse.dao.MaintenanceDao;
import com.senla.javacourse.dao.entity.Guest;
import com.senla.javacourse.dao.entity.Maintenance;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class MaintenanceDaoImpl extends AbstractDaoImpl<Maintenance> implements MaintenanceDao {
    @PersistenceContext
    private EntityManager entityManager;

    public MaintenanceDaoImpl() {
        super();
    }

    @Override
    public List<Maintenance> getAll(String fieldToSortBy) {
        String str = "select m from Maintenance m where m.guest = null and m.orderTime = null order by " + fieldToSortBy + " asc";
        TypedQuery<Maintenance> q = entityManager.createQuery(str, Maintenance.class);
        return q.getResultList();
    }

    @Override
    public Maintenance getById(long id) throws NoSuchElementException {
        Maintenance maintenance = entityManager.find(Maintenance.class, id);
        if (Objects.isNull(maintenance)) {
            throw new NoSuchElementException("Maintenance not found");
        } else {
            return maintenance;
        }
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuest(Guest guest, String fieldToSortBy) {
        TypedQuery<Maintenance> q = entityManager.createQuery(
                "select m from Maintenance m where m.guest = :guest order by " + fieldToSortBy + " asc", Maintenance.class
        );
        q.setParameter("guest", guest);
        return q.getResultList();
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
    public void updateMaintenancePrice(Maintenance maintenance, int price) {
        maintenance.setPrice(price);
        update(maintenance);
    }

    @Override
    public void addGuestMaintenance(Maintenance maintenanceInstance) {
        create(maintenanceInstance);
    }

    @Override
    public String exportData(Maintenance maintenance) {
        return maintenance.getId() + "," +
                maintenance.getName() + "," +
                maintenance.getPrice() + "," +
                maintenance.getCategory();
    }
}
