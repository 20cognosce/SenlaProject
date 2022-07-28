package com.senla.dao.impl;


import com.senla.dao.MaintenanceDao;
import com.senla.model.Maintenance;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class MaintenanceDaoImpl extends AbstractDaoImpl<Maintenance> implements MaintenanceDao {

    @Override
    public void addOrderedMaintenance(Long guestId, Long maintenanceId) {
        entityManager.createNativeQuery(
                "INSERT INTO guest_2_maintenance (guest_id, maintenance_id, order_timestamp) VALUES (?,?,?)")
                .setParameter(1, guestId)
                .setParameter(2, maintenanceId)
                .setParameter(3, LocalDateTime.now())
                .executeUpdate();
    }

    @Override
    protected Class<Maintenance> daoEntityClass() {
        return Maintenance.class;
    }
}
