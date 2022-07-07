package com.senla.javacourse.service.impl;

import com.senla.javacourse.controller.action.SortEnum;
import com.senla.javacourse.dao.MaintenanceDao;
import com.senla.javacourse.dao.entity.Guest;
import com.senla.javacourse.dao.entity.Maintenance;
import com.senla.javacourse.dao.entity.MaintenanceCategory;
import com.senla.javacourse.service.MaintenanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MaintenanceServiceImpl extends AbstractServiceImpl<Maintenance, MaintenanceDao> implements MaintenanceService {
    public MaintenanceServiceImpl() {
        super();
    }

    @Transactional
    @Override
    public void createMaintenance(String maintenanceName, int price, MaintenanceCategory category) {
        maintenanceDao.create(new Maintenance(maintenanceName, price, category, null, null));
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
    public void updateMaintenancePrice(long maintenanceId, int price) {
        maintenanceDao.updateMaintenancePrice(getById(maintenanceId), price);
    }

    @Override
    public List<Maintenance> getAllMaintenancesSorted(SortEnum sortEnum) {
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

    @Override
    public List<Maintenance> getMaintenancesOfGuestSorted(Long guestId, SortEnum sortEnum) {
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
    public void importData(List<List<String>> records) {
        records.forEach(entry -> {
            try {
                long maintenanceId = Long.parseLong(entry.get(0));
                String name = entry.get(1);
                int price = Integer.parseInt(entry.get(2));
                MaintenanceCategory maintenanceCategory = MaintenanceCategory.valueOf(entry.get(3));

                try {
                    Maintenance maintenance = getById(maintenanceId);
                    maintenance.setName(name);
                    maintenance.setPrice(price);
                    maintenance.setCategory(maintenanceCategory);
                } catch (NoSuchElementException e) {
                    createMaintenance(name, price, maintenanceCategory);
                }
            } catch (Exception e) {
                System.out.println(e.getClass().getCanonicalName() + ": "  + e.getMessage());
            }
        });
    }

    @Override
    public String getExportTitleLine() {
        return "id,Name,Price,Category";
    }

    @Override
    public String exportData(long id) throws NoSuchElementException {
        return getDefaultDao().exportData(getById(id));
    }

    @Override
    public MaintenanceDao getDefaultDao() {
        return maintenanceDao;
    }
}
