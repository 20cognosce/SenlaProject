package com.senla.service;

import com.senla.controller.action.SortEnum;
import com.senla.dao.MaintenanceDao;
import com.senla.model.Maintenance;
import com.senla.model.MaintenanceCategory;

import java.util.List;

public interface MaintenanceService extends AbstractService<Maintenance, MaintenanceDao> {
    void createMaintenance(String maintenanceName, int price, MaintenanceCategory category);
    void createMaintenance(Maintenance maintenance);
    void executeMaintenance(long guestId, long maintenanceId) throws CloneNotSupportedException;
    void updateMaintenancePrice(long maintenanceId, int price);

    List<Maintenance> sortByAddition();
    List<Maintenance> sortByPrice();
    List<Maintenance> sortByCategory();

    List<Maintenance> sortMaintenancesOfGuestByAddition(long guestId);
    List<Maintenance> sortMaintenancesOfGuestByPrice(long guestId);
    List<Maintenance> sortMaintenancesOfGuestByTime(long guestId);
}
