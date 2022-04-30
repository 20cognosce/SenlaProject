package task5.dao.impl;

import task5.dao.MaintenanceDao;
import task5.dao.entity.Guest;
import task5.dao.entity.Maintenance;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MaintenanceDaoImpl extends AbstractDaoImpl<Maintenance> implements MaintenanceDao {
    public MaintenanceDaoImpl() {
        super(Maintenance[].class);
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
    public String exportData(Maintenance maintenance) {
        return maintenance.getId() + "," +
                maintenance.getName() + "," +
                maintenance.getPrice() + "," +
                maintenance.getCategory();
    }
}
