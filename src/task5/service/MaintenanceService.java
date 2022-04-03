package task5.service;

import task5.dao.model.Guest;
import task5.dao.model.Maintenance;
import task5.dao.model.MaintenanceCategory;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public interface MaintenanceService {
    void createMaintenance(String maintenanceName, int price, MaintenanceCategory category);
    Maintenance getMaintenanceByName(String name) throws NoSuchElementException;
    Maintenance getMaintenanceById(int id) throws NoSuchElementException;
    List<Maintenance> getAll();
    String getAllAsString();
    String getAllAsString(List<Maintenance> subList);
    List<Maintenance> getSorted(List<Maintenance> subList, Comparator<Maintenance> comparator);
    String getMaintenancesOfGuest(Guest guest);
    String getMaintenancesOfGuest(Guest guest, Comparator<Maintenance> comparator);

    void executeMaintenance( int guestId, int maintenanceId);
}
