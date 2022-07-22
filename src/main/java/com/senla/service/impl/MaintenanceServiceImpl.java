package com.senla.service.impl;

import com.senla.build.config.SortEnum;
import com.senla.dao.GuestDao;
import com.senla.dao.MaintenanceDao;
import com.senla.model.Guest;
import com.senla.model.Maintenance;
import com.senla.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Transactional
    @Override
    public void executeMaintenance(long guestId, long maintenanceId) {
        Maintenance maintenanceInstance;
        Guest guest;
        guest = guestDao.getById(guestId);
        maintenanceInstance = maintenanceDao.getById(maintenanceId).getCloneInstance();
        maintenanceInstance.setGuest(guest);

        maintenanceDao.addGuestMaintenance(maintenanceInstance);
        guestDao.updateGuestPrice(guest, (guest.getPrice() + maintenanceInstance.getPrice()));
        System.out.println("Услуга " + maintenanceInstance.getName()
                + " для " + guest.getName()
                + " исполнена. Цена услуги: " + maintenanceInstance.getPrice()
                + "; Дата: " + maintenanceInstance
                .getOrderTime()
                .truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_DATE_TIME));
    }

    @Override
    @Transactional
    public void updateMaintenance(Maintenance maintenance) {
        maintenanceDao.update(maintenance);
    }

    private List<Maintenance> getAllMaintenancesSorted(SortEnum sortEnum) {
        String fieldToSort;

        switch (sortEnum) {
            case BY_ADDITION: fieldToSort = "id"; break;
            case BY_PRICE: fieldToSort = "price"; break;
            case BY_CATEGORY:  fieldToSort = "category"; break;
            case BY_TIME: fieldToSort = "orderTime"; break;
            default: throw new NoSuchElementException();
        }

        return getDefaultDao().getAll(fieldToSort);
    }

    private List<Maintenance> getMaintenancesOfGuestSorted(Long guestId, SortEnum sortEnum) {
        String fieldToSort;

        switch (sortEnum) {
            case BY_ADDITION: fieldToSort = "id"; break;
            case BY_PRICE: fieldToSort = "price"; break;
            case BY_CATEGORY:  fieldToSort = "category"; break;
            case BY_TIME: fieldToSort = "orderTime"; break;
            default: throw new NoSuchElementException();
        }

        Guest guest = guestDao.getById(guestId);
        return getDefaultDao().getMaintenancesOfGuest(guest, fieldToSort);
    }

    @Override
    public List<Maintenance> sortByAddition() {
        return getAllMaintenancesSorted(SortEnum.BY_ADDITION);
    }

    @Override
    public List<Maintenance> sortByPrice() {
        return getAllMaintenancesSorted(SortEnum.BY_PRICE);
    }

    @Override
    public List<Maintenance> sortByCategory() {
        return getAllMaintenancesSorted(SortEnum.BY_CATEGORY);
    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByAddition(long guestId) {
        return getMaintenancesOfGuestSorted(guestId, SortEnum.BY_ADDITION);
    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByPrice(long guestId) {
        return getMaintenancesOfGuestSorted(guestId, SortEnum.BY_PRICE);
    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByTime(long guestId) {
        return getMaintenancesOfGuestSorted(guestId, SortEnum.BY_TIME);
    }

    @Override
    public MaintenanceDao getDefaultDao() {
        return maintenanceDao;
    }
}
