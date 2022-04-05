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
        maintenanceDao.createMaintenance(maintenanceName, price, category);
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuest(int guestId) throws NoSuchElementException {
        return maintenanceDao.getMaintenancesOfGuest(guestDao.getById(guestId));
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuest(int guestId, Comparator<Maintenance> comparator) throws NoSuchElementException {
        return maintenanceDao.getMaintenancesOfGuest(guestDao.getById(guestId), comparator);
    }

    @Override
    public void executeMaintenance(int guestId, int maintenanceId) {
        Maintenance maintenance;
        Guest guest;
        try {
            //TODO: must get a copy of the maintenance
            maintenance = maintenanceDao.getById(maintenanceId);
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
        maintenance.setOrderTime(time);
        guest.addMaintenance(maintenance);
        guest.setPrice(guest.getPrice() + maintenance.getPrice());
    }

    @Override
    public void setPrice(int maintenanceId, int price) {
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
}
