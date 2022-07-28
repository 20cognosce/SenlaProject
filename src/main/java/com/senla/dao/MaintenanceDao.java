package com.senla.dao;

import com.senla.model.Maintenance;

public interface MaintenanceDao extends AbstractDao<Maintenance> {

    void addOrderedMaintenance(Long guestId, Long maintenanceId);
}
