package task5.service.impl;

import task5.controller.PropertiesUtil;
import task5.controller.action.SortEnum;
import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;
import task5.dao.entity.Guest;
import task5.dao.entity.Room;
import task5.dao.entity.RoomStatus;
import task5.service.RoomService;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public class RoomServiceImpl extends AbstractServiceImpl<Room, RoomDao> implements RoomService {
    String changeRoomStatusPossibility = PropertiesUtil.property.getProperty("ChangeRoomStatusPossibility");
    int lastNGuests = Integer.parseInt(PropertiesUtil.property.getProperty("GuestsNumberInRoomHistory"));

    public RoomServiceImpl (GuestDao guestDao, RoomDao roomDao, MaintenanceDao maintenanceDao) {
        super(roomDao, guestDao, roomDao, maintenanceDao);
    }

    @Override
    public List<Guest> getLastNGuests(long roomId) throws NoSuchElementException {
        List<Long> archivedGuestIdList = roomDao.getById(roomId).getArchivedGuestIdList();
        List<Guest> guestList = new ArrayList<>();
        archivedGuestIdList.forEach(id -> guestList.add(guestDao.getFromArchivedRepositoryById(id)));
        return guestList.stream().sorted(
                Comparator.comparing(Guest::getCheckInDate).reversed()).limit(lastNGuests).collect(Collectors.toList());
    }

    @Override
    public List<Guest> getGuestsList(long roomId) throws NoSuchElementException {
        List<Guest> guestList = new ArrayList<>();
        getById(roomId).getCurrentGuestIdList().forEach(guestId -> guestList.add(guestDao.getById(guestId)));
        return guestList;
    }

    @Override
    public void createRoom(String name, int capacity, int starsNumber, RoomStatus roomStatus, int price) {
        roomDao.synchronizeNextSuppliedId(getAll().get(getAll().size() - 1).getId());
        roomDao.addToRepo(new Room(roomDao.supplyId(), name, capacity, starsNumber, roomStatus, price));
    }

    @Override
    public List<Room> getFree() {
        return roomDao.getFree();
    }

    @Override
    public List<Room> getFree(LocalDate asAtSpecificDate) {
        return roomDao.getFree(asAtSpecificDate, guestDao);
    }


    @Override
    public void setPrice(long roomId, int price) throws NoSuchElementException {
        roomDao.getById(roomId).setPrice(price);
    }

    @Override
    public void setStatus(long roomId, RoomStatus roomStatus) throws ServiceUnavailableException {
        if ("no".equals(changeRoomStatusPossibility)) {
            throw new ServiceUnavailableException("Опция недоступна");
        }
        roomDao.setStatus(roomId, roomStatus);
    }

    @Override
    public List<Room> sortByAddition() {
        return getSorted(getAll(), SortEnum.BY_ADDITION);
    }

    @Override
    public List<Room> sortByCapacity() {
        return getSorted(getAll(), SortEnum.BY_CAPACITY);
    }

    @Override
    public List<Room> sortByPrice() {
        return getSorted(getAll(), SortEnum.BY_PRICE);
    }

    @Override
    public List<Room> sortByStars() {
        return getSorted(getAll(), SortEnum.BY_STARS);
    }

    @Override
    public List<Room> sortFreeRoomsByAddition(LocalDate specificDate) {
        return getSorted(getFree(specificDate), SortEnum.BY_CAPACITY);
    }

    @Override
    public List<Room> sortFreeRoomsByCapacity(LocalDate specificDate) {
        return getSorted(getFree(specificDate), SortEnum.BY_CAPACITY);
    }

    @Override
    public List<Room> sortFreeRoomsByPrice(LocalDate specificDate) {
        return getSorted(getFree(specificDate), SortEnum.BY_PRICE);
    }

    @Override
    public List<Room> sortFreeRoomsByStars(LocalDate specificDate) {
        return getSorted(getFree(specificDate), SortEnum.BY_STARS);
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
                    roomDao.synchronizeNextSuppliedId(roomId);
                    createRoom(name, capacity, stars, roomStatus, price);
                }
            } catch (Exception e) {
                System.out.println(e.getClass().getCanonicalName() + ": "  + e.getMessage());
            }
        });
    }

    @Override
    public List<Room> getSorted(List<Room> listToSort, SortEnum sortBy) throws NoSuchElementException {
        switch (sortBy) {
            case BY_ADDITION: return currentDao.getSorted(listToSort, Comparator.comparingLong(Room::getId));
            case BY_PRICE: return currentDao.getSorted(listToSort, Comparator.comparingInt(Room::getPrice));
            case BY_CAPACITY: return currentDao.getSorted(listToSort, Comparator.comparingInt(Room::getCapacity));
            case BY_STARS: return currentDao.getSorted(listToSort, Comparator.comparingInt(Room::getStarsNumber));
        }
        throw new NoSuchElementException();
    }

    @Override
    public String getExportTitleLine() {
        return "id,Name,Capacity,Stars,Status,Price";
    }

    @Override
    public String exportData(long id) throws NoSuchElementException {
        return currentDao.exportData(getById(id));
    }
}
