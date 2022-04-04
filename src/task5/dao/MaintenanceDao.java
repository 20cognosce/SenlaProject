package task5.dao;

import task5.dao.model.Guest;
import task5.dao.model.Maintenance;
import task5.dao.model.MaintenanceCategory;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public interface MaintenanceDao {
    void createMaintenance(String maintenanceName, int price, MaintenanceCategory category);

    Maintenance getMaintenanceByName(String name) throws NoSuchElementException;
    Maintenance getMaintenanceById(int id) throws NoSuchElementException;
    List<Maintenance> getAll();
    String getAllAsString();
    String getAsString(List<Maintenance> subList);
    List<Maintenance> getMaintenancesOfGuest(Guest guest);
    List<Maintenance> getMaintenancesOfGuest(Guest guest, Comparator<Maintenance> comparator);
    List<Maintenance> getSorted(List<Maintenance> listToSort, Comparator<Maintenance> comparator);
}
