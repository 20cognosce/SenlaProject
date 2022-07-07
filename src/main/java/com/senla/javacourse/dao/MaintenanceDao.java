package com.senla.javacourse.dao;

import com.senla.javacourse.dao.entity.Guest;
import com.senla.javacourse.dao.entity.Maintenance;

import java.util.Comparator;
import java.util.List;

public interface MaintenanceDao extends AbstractDao<Maintenance> {
    List<Maintenance> getMaintenancesOfGuest(Guest guest);
    List<Maintenance> getMaintenancesOfGuest(Guest guest, Comparator<Maintenance> comparator);
    List<Maintenance> getMaintenancesOfGuest(Guest guest, String fieldToSortBy);
    void updateMaintenancePrice(Maintenance maintenance, int price);
    void addGuestMaintenance(Maintenance maintenanceInstance);
}
