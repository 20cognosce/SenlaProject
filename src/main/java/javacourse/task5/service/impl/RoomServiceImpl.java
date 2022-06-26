package javacourse.task5.service.impl;

import javacourse.task5.build.factory.Component;
import javacourse.task5.build.property.PropertiesUtil;
import javacourse.task5.controller.action.SortEnum;
import javacourse.task5.dao.RoomDao;
import javacourse.task5.dao.entity.Guest;
import javacourse.task5.dao.entity.Maintenance;
import javacourse.task5.dao.entity.Room;
import javacourse.task5.dao.entity.RoomStatus;
import javacourse.task5.service.RoomService;

import javax.naming.ServiceUnavailableException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class RoomServiceImpl extends AbstractServiceImpl<Room, RoomDao> implements RoomService {
    public RoomServiceImpl() {
        super();
    }
    String changeRoomStatusPossibility = PropertiesUtil.property.getProperty("ChangeRoomStatusPossibility");
    int lastNGuests = Integer.parseInt(PropertiesUtil.property.getProperty("GuestsNumberInRoomHistory"));

    @Override
    public List<Guest> getLastNGuests(long roomId) throws NoSuchElementException {
        List<Guest> currentGuestList = roomDao.getById(roomId).getCurrentGuestList();
        return currentGuestList.stream().sorted(
                Comparator.comparing(Guest::getCheckInDate).reversed()).limit(lastNGuests).collect(Collectors.toList());
    }

    @Override
    public List<Guest> getGuestsList(long roomId) throws NoSuchElementException {
        return getById(roomId).getCurrentGuestList();
    }

    @Override
    public void createRoom(String name, int capacity, int starsNumber, RoomStatus roomStatus, int price) {
        roomDao.addToRepo(new Room(name, capacity, starsNumber, roomStatus, price));
    }

    @Override
    public List<Room> getFree() {
        return roomDao.getFree();
    }

    @Override
    public List<Room> getFree(LocalDate asAtSpecificDate) {
        return getFreeRoomsSorted(asAtSpecificDate, SortEnum.BY_ADDITION);
    }


    @Override
    public void updateRoomPrice(long roomId, int price) throws NoSuchElementException {
        roomDao.updateRoomPrice(getById(roomId), price);
    }

    @Override
    public void updateRoomStatus(long roomId, RoomStatus roomStatus) throws ServiceUnavailableException {
        if ("no".equals(changeRoomStatusPossibility)) {
            throw new ServiceUnavailableException("Опция недоступна");
        }
        roomDao.updateRoomStatus(getById(roomId), roomStatus);
    }

    @Override
    public List<Room> sortByAddition() {
        return getRoomsSorted(SortEnum.BY_ADDITION);
    }

    @Override
    public List<Room> sortByCapacity() {
        return getRoomsSorted(SortEnum.BY_CAPACITY);
    }

    @Override
    public List<Room> sortByPrice() {
        return getRoomsSorted(SortEnum.BY_PRICE);
    }

    @Override
    public List<Room> sortByStars() {
        return getRoomsSorted(SortEnum.BY_STARS);
    }

    @Override
    public List<Room> sortFreeRoomsByAddition(LocalDate specificDate) {
        return getFreeRoomsSorted(specificDate, SortEnum.BY_CAPACITY);
    }

    @Override
    public List<Room> sortFreeRoomsByCapacity(LocalDate specificDate) {
        return getFreeRoomsSorted(specificDate, SortEnum.BY_CAPACITY);
    }

    @Override
    public List<Room> sortFreeRoomsByPrice(LocalDate specificDate) {
        return getFreeRoomsSorted(specificDate, SortEnum.BY_PRICE);
    }

    @Override
    public List<Room> sortFreeRoomsByStars(LocalDate specificDate) {
        return getFreeRoomsSorted(specificDate, SortEnum.BY_STARS);
    }

    @Override
    public void importData(List<List<String>> records) {
        records.forEach(entry -> {
            try {
                long roomId = Long.parseLong(entry.get(0));
                String name = entry.get(1);
                int capacity = Integer.parseInt(entry.get(2));
                int stars = Integer.parseInt(entry.get(3));
                RoomStatus roomStatus = RoomStatus.valueOf(entry.get(4));
                int price = Integer.parseInt(entry.get(5));

                try {
                    Room room = getById(roomId);
                    room.setName(name);
                    room.setCapacity(capacity);
                    room.setStarsNumber(stars);
                    room.setRoomStatus(roomStatus);
                    room.setPrice(price);
                } catch (NoSuchElementException e) {
                    createRoom(name, capacity, stars, roomStatus, price);
                }
            } catch (Exception e) {
                System.out.println(e.getClass().getCanonicalName() + ": "  + e.getMessage());
            }
        });
    }

    @Override
    public List<Room> getRoomsSorted(SortEnum sortEnum) {
        var ref = new Object() {
            String fieldToSort;
            List<Room> result;
        };

        switch (sortEnum) {
            case BY_ADDITION: ref.fieldToSort = "id"; break;
            case BY_PRICE: ref.fieldToSort = "price"; break;
            case BY_CAPACITY:  ref.fieldToSort = "capacity"; break;
            case BY_STARS: ref.fieldToSort = "starsNumber";
        }

        getDefaultDao().openSessionAndExecuteTransactionTask((session, criteriaBuilder) -> {
            CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
            Root<Room> root = criteriaQuery.from(Room.class);
            List<Order> orderList = new ArrayList<>();
            orderList.add(criteriaBuilder.asc(root.get(ref.fieldToSort)));
            TypedQuery<Room> query
                    = session.createQuery(criteriaQuery.select(root).orderBy(orderList));
            ref.result = query.getResultList();
        });

        return ref.result;
    }

    public List<Room> getFreeRoomsSorted(LocalDate specificDate, SortEnum sortEnum) {
        String fieldToSort;

        switch (sortEnum) {
            case BY_ADDITION: fieldToSort = "id"; break;
            case BY_PRICE: fieldToSort = "price"; break;
            case BY_CAPACITY:  fieldToSort = "capacity"; break;
            case BY_STARS: fieldToSort = "starsNumber"; break;
            default: throw new NoSuchElementException("Such sortEnum does not exist");
        }

        return getDefaultDao().getFree(specificDate, fieldToSort);
    }

    @Override
    public String getExportTitleLine() {
        return "id,Name,Capacity,Stars,Status,Price";
    }

    @Override
    public String exportData(long id) throws NoSuchElementException {
        return getDefaultDao().exportData(getById(id));
    }

    @Override
    public RoomDao getDefaultDao() {
        return roomDao;
    }
}
