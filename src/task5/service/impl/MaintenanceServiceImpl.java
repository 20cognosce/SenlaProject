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
    public String getAllAsString(List<Maintenance> subList) {
        return maintenanceDao.getAllAsString(subList);
    }


    @Override
    public List<Maintenance> getSorted(List<Maintenance> subList, Comparator<Maintenance> comparator) {
        return maintenanceDao.getSorted(subList, comparator);
    }

    @Override
    public String getMaintenancesOfGuest(Guest guest) {
        return maintenanceDao.getMaintenancesOfGuest(guest);
    }

    @Override
    public String getMaintenancesOfGuest(Guest guest, Comparator<Maintenance> comparator) {
        return null;
    }

    @Override
    public void executeMaintenance(int guestId, int maintenanceId) {
        Maintenance maintenance;
        Guest guest;
        try {
            maintenance = maintenanceDao.getMaintenanceById(maintenanceId);
            guest = guestDao.getGuestById(guestId);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        LocalDateTime time = LocalDateTime.now();
        System.out.println("Услуга " + maintenance.getName()
                + " для " + guest.getFullName()
                + " исполнена. Цена услуги: " + maintenance.getPrice()
                + "; Дата: " + time.truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME));
        maintenance.setOrderTime(time);
        guest.getOrderedMaintenances().add(maintenance);
        guest.setPayment(guest.getPayment() + maintenance.getPrice());
    }
}
