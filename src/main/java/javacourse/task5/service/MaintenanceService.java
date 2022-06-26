package javacourse.task5.service;

import javacourse.task5.controller.action.SortEnum;
import javacourse.task5.dao.MaintenanceDao;
import javacourse.task5.dao.entity.Maintenance;
import javacourse.task5.dao.entity.MaintenanceCategory;

import java.util.Comparator;
import java.util.List;

public interface MaintenanceService extends AbstractService<Maintenance, MaintenanceDao> {
    void createMaintenance(String maintenanceName, int price, MaintenanceCategory category);
    List<Maintenance> getMaintenancesOfGuest(long guestId);
    List<Maintenance> getMaintenancesOfGuest(long guestId, Comparator<Maintenance> comparator);

    void executeMaintenance(long guestId, long maintenanceId);
    void updateMaintenancePrice(long maintenanceId, int price);

    List<Maintenance> sortByAddition();
    List<Maintenance> sortByPrice();
    List<Maintenance> sortByCategory();

    List<Maintenance> sortMaintenancesOfGuestByAddition(long guestId);
    List<Maintenance> sortMaintenancesOfGuestByPrice(long guestId);
    List<Maintenance> sortMaintenancesOfGuestByTime(long guestId);
}
