package task5.dao;

import task5.dao.entity.Guest;
import task5.dao.entity.Maintenance;

import java.util.Comparator;
import java.util.List;

public interface MaintenanceDao extends AbstractDao<Maintenance> {
    List<Maintenance> getMaintenancesOfGuest(Guest guest);
    List<Maintenance> getMaintenancesOfGuest(Guest guest, Comparator<Maintenance> comparator);
}
