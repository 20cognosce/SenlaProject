package com.senla.service;

import com.senla.dao.MaintenanceDao;
import com.senla.model.Maintenance;

import java.util.List;

public interface MaintenanceService extends AbstractService<Maintenance, MaintenanceDao> {

    void createMaintenance(Maintenance maintenance);
    void executeMaintenance(long guestId, long maintenanceId);
    void updateMaintenance(Maintenance maintenance);

    List<Maintenance> sortByAddition(String order);
    List<Maintenance> sortByPrice(String order);
    List<Maintenance> sortByCategory(String order);


    List<Maintenance> sortMaintenancesOfGuestByAddition(long guestId, String order);
    List<Maintenance> sortMaintenancesOfGuestByPrice(long guestId, String order);
    List<Maintenance> sortMaintenancesOfGuestByTime(long guestId, String order);
}
