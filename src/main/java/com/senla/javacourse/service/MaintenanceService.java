package com.senla.javacourse.service;

import com.senla.javacourse.controller.action.SortEnum;
import com.senla.javacourse.dao.MaintenanceDao;
import com.senla.javacourse.dao.entity.Maintenance;
import com.senla.javacourse.dao.entity.MaintenanceCategory;

import java.util.List;

public interface MaintenanceService extends AbstractService<Maintenance, MaintenanceDao> {
    void createMaintenance(String maintenanceName, int price, MaintenanceCategory category);
    List<Maintenance> getAllMaintenancesSorted(SortEnum sortEnum);
    List<Maintenance> getMaintenancesOfGuestSorted(Long guestId, SortEnum sortEnum);

    void executeMaintenance(long guestId, long maintenanceId) throws CloneNotSupportedException;
    void updateMaintenancePrice(long maintenanceId, int price);

    List<Maintenance> sortByAddition();
    List<Maintenance> sortByPrice();
    List<Maintenance> sortByCategory();

    List<Maintenance> sortMaintenancesOfGuestByAddition(long guestId);
    List<Maintenance> sortMaintenancesOfGuestByPrice(long guestId);
    List<Maintenance> sortMaintenancesOfGuestByTime(long guestId);
}
