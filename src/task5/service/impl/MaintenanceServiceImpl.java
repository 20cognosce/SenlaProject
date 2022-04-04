package task5.service.impl;

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


public class MaintenanceServiceImpl extends AbstractServiceImpl implements MaintenanceService {
    public MaintenanceServiceImpl (GuestDao guestDao, RoomDao roomDao, MaintenanceDao maintenanceDao) {
        super(guestDao, roomDao, maintenanceDao);
    }

    @Override
    public void createMaintenance(String maintenanceName, int price, MaintenanceCategory category) {
        maintenanceDao.createMaintenance(maintenanceName, price, category);
    }

    @Override
    public Maintenance getMaintenanceByName(String name) throws NoSuchElementException {
        return null;
    }

    @Override
    public Maintenance getMaintenanceById(int id) throws NoSuchElementException {
        return maintenanceDao.getMaintenanceById(id);
    }

    @Override
    public List<Maintenance> getAll() {
        return maintenanceDao.getAll();
    }

    @Override
    public String getAllAsString() {
        return maintenanceDao.getAllAsString();
    }

    @Override
    public String getAsString(List<Maintenance> subList) {
        return maintenanceDao.getAsString(subList);
    }


    @Override
    public List<Maintenance> getSorted(List<Maintenance> subList, Comparator<Maintenance> comparator) {
        return maintenanceDao.getSorted(subList, comparator);
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuest(int guestId) throws NoSuchElementException {
        return maintenanceDao.getMaintenancesOfGuest(guestDao.getGuestById(guestId));
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuest(int guestId, Comparator<Maintenance> comparator) throws NoSuchElementException {
        return maintenanceDao.getMaintenancesOfGuest(guestDao.getGuestById(guestId), comparator);
    }

    @Override
    public void executeMaintenance(int guestId, int maintenanceId) {
        Maintenance maintenance;
        Guest guest;
        try {
            //TODO: must get a copy of the maintenance
            maintenance = maintenanceDao.getMaintenanceById(maintenanceId);
            guest = guestDao.getGuestById(guestId);
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
}
