package com.senla.dao.impl;


import com.senla.dao.MaintenanceDao;
import com.senla.model.Guest;
import com.senla.model.Maintenance;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@NoArgsConstructor
public class MaintenanceDaoImpl extends AbstractDaoImpl<Maintenance> implements MaintenanceDao {

    @Override
    public List<Maintenance> getMaintenancesOfGuest(Guest guest, String fieldToSortBy) {
        TypedQuery<Maintenance> q = entityManager.createQuery(
                "select m from Maintenance m where m.guest = :guest order by " + fieldToSortBy + " asc", Maintenance.class
        );
        q.setParameter("guest", guest);
        return q.getResultList();
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuest(Guest guest, Comparator<Maintenance> comparator) {
        return getMaintenancesOfGuest(guest, "id").stream().sorted(comparator).collect(Collectors.toList());
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

    @Override
    protected Class<Maintenance> daoEntityClass() {
        return Maintenance.class;
    }
}
