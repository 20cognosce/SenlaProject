package task5.service;

import task5.controller.action.SortEnum;
import task5.dao.model.Maintenance;
import task5.dao.model.MaintenanceCategory;

import java.util.Comparator;
import java.util.List;

public interface MaintenanceService extends AbstractService<Maintenance>{
    void createMaintenance(String maintenanceName, int price, MaintenanceCategory category);
    List<Maintenance> getMaintenancesOfGuest(int guestId);
    List<Maintenance> getMaintenancesOfGuest(int guestId, Comparator<Maintenance> comparator);

    void executeMaintenance( int guestId, int maintenanceId);
    void setPrice(int maintenanceId, int price);
    List<Maintenance> getSorted(List<Maintenance> listToSort, SortEnum sortBy);
}
