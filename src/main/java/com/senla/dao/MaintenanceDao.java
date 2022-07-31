package com.senla.dao;

import com.senla.model.Maintenance;

import java.util.List;

public interface MaintenanceDao extends AbstractDao<Maintenance> {

    void addOrderedMaintenance(Long guestId, Long maintenanceId);
    List<Maintenance> getMaintenancesOfGuestSorted(Long guestId, String fieldToSortBy, String order);
}
