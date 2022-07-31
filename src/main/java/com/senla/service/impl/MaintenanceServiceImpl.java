package com.senla.service.impl;

import com.senla.dao.GuestDao;
import com.senla.dao.MaintenanceDao;
import com.senla.model.Guest;
import com.senla.model.Maintenance;
import com.senla.service.MaintenanceService;
import com.senla.util.SortEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl extends AbstractServiceImpl<Maintenance, MaintenanceDao> implements MaintenanceService {

    private final MaintenanceDao maintenanceDao;
    private final GuestDao guestDao;

    @Transactional
    @Override
    public void createMaintenance(Maintenance maintenance) {
        maintenanceDao.create(maintenance);
    }

    @Override
    @Transactional
    public void updateMaintenance(Maintenance maintenance) {
        maintenanceDao.update(maintenance);
    }

    @Transactional
    @Override
    public void executeMaintenance(long guestId, long maintenanceId) {
        Guest guest = guestDao.getById(guestId);
        Maintenance maintenance = maintenanceDao.getById(maintenanceId);
        maintenanceDao.addOrderedMaintenance(guestId, maintenanceId);
        guestDao.updateGuestPrice(guest, (guest.getPrice() + maintenance.getPrice()));
    }

    @Override
    public List<Maintenance> sortByAddition(String order) {
        return getAllMaintenancesSorted(SortEnum.BY_ADDITION, order);
    }

    @Override
    public List<Maintenance> sortByPrice(String order) {
        return getAllMaintenancesSorted(SortEnum.BY_PRICE, order);
    }

    @Override
    public List<Maintenance> sortByCategory(String order) {
        return getAllMaintenancesSorted(SortEnum.BY_CATEGORY, order);
    }

    public List<Maintenance> getAllMaintenancesSorted(SortEnum sortEnum, String order) {
        String fieldToSort = getFieldToSortFromEnum(sortEnum);
        return getAll(fieldToSort, order);
    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByAddition(long guestId, String order) {
        return getMaintenancesOfGuestSorted(guestId, SortEnum.BY_ADDITION, order);
    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByPrice(long guestId, String order) {
        return getMaintenancesOfGuestSorted(guestId, SortEnum.BY_PRICE, order);
    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByTime(long guestId, String order) {
        return getMaintenancesOfGuestSorted(guestId, SortEnum.BY_TIME, order);
    }

    public List<Maintenance> getMaintenancesOfGuestSorted(Long guestId, SortEnum sortEnum, String order) {
        String fieldToSort = getFieldToSortFromEnum(sortEnum);
        return getDefaultDao().getMaintenancesOfGuestSorted(guestId, fieldToSort, order);
    }

    @Override
    public MaintenanceDao getDefaultDao() {
        return maintenanceDao;
    }
}
