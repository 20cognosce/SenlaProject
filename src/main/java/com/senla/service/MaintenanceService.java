package com.senla.service;

import com.senla.dao.MaintenanceDao;
import com.senla.model.Maintenance;

import java.util.List;

public interface MaintenanceService extends AbstractService<Maintenance, MaintenanceDao> {

    void createMaintenance(Maintenance maintenance);
    void executeMaintenance(long guestId, long maintenanceId);
    void updateMaintenance(Maintenance maintenance);

    List<Maintenance> sortByAddition();
    List<Maintenance> sortByPrice();
    List<Maintenance> sortByCategory();

    List<Maintenance> sortMaintenancesOfGuestByAddition(long guestId);
    List<Maintenance> sortMaintenancesOfGuestByPrice(long guestId);
    List<Maintenance> sortMaintenancesOfGuestByTime(long guestId);
}
