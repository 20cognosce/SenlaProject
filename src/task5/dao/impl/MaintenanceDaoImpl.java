package task5.dao.impl;

import task5.dao.MaintenanceDao;
import task5.dao.model.Guest;
import task5.dao.model.IdSupplier;
import task5.dao.model.Maintenance;
import task5.dao.model.MaintenanceCategory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MaintenanceDaoImpl extends AbstractDaoImpl<Maintenance> implements MaintenanceDao {
    private final IdSupplier idSupplier = new IdSupplier();

    public MaintenanceDaoImpl() {
        super();
    }

    @Override
    public void createMaintenance(String maintenanceName, int price, MaintenanceCategory category) {
        addToRepo(new Maintenance(idSupplier.supplyId(), maintenanceName, price, category));
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuest(Guest guest) {
        return guest.getOrderedMaintenances();
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuest(Guest guest, Comparator<Maintenance> comparator) {
        return guest.getOrderedMaintenances().stream().sorted(comparator).collect(Collectors.toList());
    }
}
