package task5.service.impl;

import task5.controller.action.SortEnum;
import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;
import task5.dao.model.Guest;
import task5.dao.model.Maintenance;
import task5.dao.model.MaintenanceCategory;
import task5.service.MaintenanceService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;


public class MaintenanceServiceImpl extends AbstractServiceImpl<Maintenance, MaintenanceDao> implements MaintenanceService {
    public MaintenanceServiceImpl (GuestDao guestDao, RoomDao roomDao, MaintenanceDao maintenanceDao) {
        super(maintenanceDao, guestDao, roomDao, maintenanceDao);
    }

    @Override
    public void createMaintenance(String maintenanceName, int price, MaintenanceCategory category) {
        maintenanceDao.addToRepo(new Maintenance(maintenanceDao.supplyId(), maintenanceName, price, category));
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuest(long guestId) throws NoSuchElementException {
        return maintenanceDao.getMaintenancesOfGuest(guestDao.getById(guestId));
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuest(long guestId, Comparator<Maintenance> comparator) throws NoSuchElementException {
        return maintenanceDao.getMaintenancesOfGuest(guestDao.getById(guestId), comparator);
    }

    @Override
    public void executeMaintenance(long guestId, long maintenanceId) {
        Maintenance maintenance;
        Guest guest;
        try {
            maintenance = maintenanceDao.getById(maintenanceId).clone();
            guest = guestDao.getById(guestId);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        LocalDateTime time = LocalDateTime.now();
        System.out.println("Услуга " + maintenance.getName()
                + " для " + guest.getName()
                + " исполнена. Цена услуги: " + maintenance.getPrice()
                + "; Дата: " + time.truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME));
        guest.addMaintenance(maintenance);
        guest.setPrice(guest.getPrice() + maintenance.getPrice());
    }

    @Override
    public void setPrice(long maintenanceId, int price) {
        maintenanceDao.getById(maintenanceId).setPrice(price);
    }

    @Override
    public List<Maintenance> getSorted(List<Maintenance> listToSort, SortEnum sortBy) throws NoSuchElementException {
        switch (sortBy) {
            case BY_ADDITION: return currentDao.getSorted(listToSort, Comparator.comparingLong(Maintenance::getId));
            case BY_PRICE: return currentDao.getSorted(listToSort, Comparator.comparingInt(Maintenance::getPrice));
            case BY_CATEGORY: return currentDao.getSorted(listToSort, Comparator.comparing(Maintenance::getCategory));
            case BY_TIME: return currentDao.getSorted(listToSort, Comparator.comparing(Maintenance::getOrderTime));
        }
        throw new NoSuchElementException();
    }

    @Override
    public List<Maintenance> sortByAddition() {
        return getSorted(getAll(), SortEnum.BY_ADDITION);
    }

    @Override
    public List<Maintenance> sortByPrice() {
        return getSorted(getAll(), SortEnum.BY_PRICE);
    }

    @Override
    public List<Maintenance> sortByCategory() {
        return getSorted(getAll(), SortEnum.BY_CATEGORY);
    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByAddition(long guestId) {
        return getSorted(getMaintenancesOfGuest(guestId), SortEnum.BY_ADDITION);
    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByPrice(long guestId) {
        return getSorted(getMaintenancesOfGuest(guestId), SortEnum.BY_PRICE);
    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByTime(long guestId) {
        return getSorted(getMaintenancesOfGuest(guestId), SortEnum.BY_TIME);
    }

    @Override
    public void importMaintenanceRecords(List<List<String>> records) {
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
                    maintenanceDao.synchronizeNextSuppliedId(maintenanceId);
                    createMaintenance(name, price, maintenanceCategory);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public String convertDataToExportFormat(long id) throws NoSuchElementException {
        return currentDao.convertDataToExportFormat(getById(id));
    }
}
