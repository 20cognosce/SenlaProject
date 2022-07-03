package com.senla.javacourse.service.impl;

import com.senla.javacourse.controller.action.SortEnum;
import com.senla.javacourse.dao.entity.Maintenance;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.dao.MaintenanceDao;
import com.senla.javacourse.dao.entity.Guest;
import com.senla.javacourse.dao.entity.MaintenanceCategory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Component
public class MaintenanceServiceImpl extends AbstractServiceImpl<Maintenance, MaintenanceDao> implements MaintenanceService {
    public MaintenanceServiceImpl() {
        super();
    }

    @Override
    public void createMaintenance(String maintenanceName, int price, MaintenanceCategory category) {
        maintenanceDao.addToRepo(new Maintenance(maintenanceName, price, category, null, null));
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
        Maintenance maintenanceInstance;
        Guest guest;
        try {
            guest = guestDao.getById(guestId);
            maintenanceInstance = maintenanceDao.getById(maintenanceId).clone();
            maintenanceInstance.setGuest(guest);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

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
    public void updateMaintenancePrice(long maintenanceId, int price) {
        maintenanceDao.updateMaintenancePrice(getById(maintenanceId), price);
    }


    public List<Maintenance> getMaintenancesSorted(Long guestId, SortEnum sortEnum) {
        var ref = new Object() {
            String fieldToSort;
            List<Maintenance> result;
        };

        switch (sortEnum) {
            case BY_ADDITION: ref.fieldToSort = "id"; break;
            case BY_PRICE: ref.fieldToSort = "price"; break;
            case BY_CATEGORY:  ref.fieldToSort = "category"; break;
            case BY_TIME: ref.fieldToSort = "orderTime";
        }

        getDefaultDao().openSessionAndExecuteTransactionTask((session, criteriaBuilder) -> {
            CriteriaQuery<Maintenance> criteriaQuery = criteriaBuilder.createQuery(Maintenance.class);
            Root<Maintenance> root = criteriaQuery.from(Maintenance.class);

            Predicate predicate;
            if (guestId == 0L) {
                Predicate predicateForGuestId = criteriaBuilder.isNull(root.get("guest"));
                Predicate predicateForTimeStamp = criteriaBuilder.isNull(root.get("orderTime"));
                predicate = criteriaBuilder.and(predicateForGuestId, predicateForTimeStamp);
            } else {
                predicate = criteriaBuilder.equal(root.get("guest"), guestDao.getById(guestId));
            }

            List<Order> orderList = new ArrayList<>();
            orderList.add(criteriaBuilder.asc(root.get(ref.fieldToSort)));
            TypedQuery<Maintenance> query
                    = session.createQuery(criteriaQuery.select(root).where(predicate).orderBy(orderList));
            ref.result = query.getResultList();
        });

        return ref.result;
    }

    @Override
    public List<Maintenance> sortByAddition() {
        return getMaintenancesSorted(0L, SortEnum.BY_ADDITION);
    }

    @Override
    public List<Maintenance> sortByPrice() {
        return getMaintenancesSorted(0L, SortEnum.BY_PRICE);
    }

    @Override
    public List<Maintenance> sortByCategory() {
        return getMaintenancesSorted(0L, SortEnum.BY_CATEGORY);
    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByAddition(long guestId) {
        return getMaintenancesSorted(guestId, SortEnum.BY_ADDITION);
    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByPrice(long guestId) {
        return getMaintenancesSorted(guestId, SortEnum.BY_PRICE);

    }

    @Override
    public List<Maintenance> sortMaintenancesOfGuestByTime(long guestId) {
        return getMaintenancesSorted(guestId, SortEnum.BY_TIME);
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
