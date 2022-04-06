package task5.service;

import task5.controller.action.SortEnum;
import task5.dao.model.Maintenance;
import task5.dao.model.MaintenanceCategory;

import java.util.Comparator;
import java.util.List;

public interface MaintenanceService extends AbstractService<Maintenance>{
    void createMaintenance(String maintenanceName, int price, MaintenanceCategory category);
    List<Maintenance> getMaintenancesOfGuest(long guestId);
    List<Maintenance> getMaintenancesOfGuest(long guestId, Comparator<Maintenance> comparator);

    void executeMaintenance( long guestId, long maintenanceId);
    void setPrice(long maintenanceId, int price);
    List<Maintenance> getSorted(List<Maintenance> listToSort, SortEnum sortBy);

    List<Maintenance> sortByAddition();
    List<Maintenance> sortByPrice();
    List<Maintenance> sortByCategory();

    List<Maintenance> sortMaintenancesOfGuestByAddition(long guestId);
    List<Maintenance> sortMaintenancesOfGuestByPrice(long guestId);
    List<Maintenance> sortMaintenancesOfGuestByTime(long guestId);
}
