package com.senla.javacourse.dao.impl;


import com.senla.javacourse.dao.entity.Maintenance;
import com.senla.javacourse.dao.MaintenanceDao;
import com.senla.javacourse.dao.entity.Guest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MaintenanceDaoImpl extends AbstractDaoImpl<Maintenance> implements MaintenanceDao {
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
    public void updateMaintenancePrice(Maintenance maintenance, int price) {
        openSessionAndExecuteTransactionTask(((session, criteriaBuilder) -> {
            maintenance.setPrice(price);
            session.update(maintenance);
        }));
    }

    @Override
    public void addGuestMaintenance(Maintenance maintenanceInstance) {
        addToRepo(maintenanceInstance);
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
}
