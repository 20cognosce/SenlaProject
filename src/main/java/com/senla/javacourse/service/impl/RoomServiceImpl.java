package com.senla.javacourse.service.impl;

import com.senla.javacourse.controller.action.SortEnum;
import com.senla.javacourse.dao.RoomDao;
import com.senla.javacourse.dao.entity.Guest;
import com.senla.javacourse.dao.entity.Room;
import com.senla.javacourse.dao.entity.RoomStatus;
import com.senla.javacourse.service.RoomService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Component
public class RoomServiceImpl extends AbstractServiceImpl<Room, RoomDao> implements RoomService {
    @Value("${ChangeRoomStatusPossibility}")
    String changeRoomStatusPossibility;
    @Value("${GuestsNumberInRoomHistory}")
    int lastNGuests;

    public RoomServiceImpl() {
        super();
    }

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
    @Transactional
    public void createRoom(String name, int capacity, int starsNumber, RoomStatus roomStatus, int price) {
        roomDao.create(new Room(name, capacity, starsNumber, roomStatus, price));
    }

    @Override
    @Transactional
    public void updateRoomPrice(long roomId, int price) throws NoSuchElementException {
        roomDao.updateRoomPrice(getById(roomId), price);
    }

    @Override
    @Transactional
    public void updateRoomStatus(long roomId, RoomStatus roomStatus) throws ServiceUnavailableException {
        if ("no".equals(changeRoomStatusPossibility)) {
            throw new ServiceUnavailableException("Опция недоступна");
        }
        roomDao.updateRoomStatus(getById(roomId), roomStatus);
    }

    public List<Room> getRoomsSorted(SortEnum sortEnum) {
        String fieldToSort;

        switch (sortEnum) {
            case BY_ADDITION: fieldToSort = "id"; break;
            case BY_PRICE: fieldToSort = "price"; break;
            case BY_CAPACITY:  fieldToSort = "capacity"; break;
            case BY_STARS: fieldToSort = "starsNumber"; break;
            default: throw new NoSuchElementException("Such sortEnum does not exist");
        }

        return getDefaultDao().getAll(fieldToSort);
    }

    @Transactional
    public List<Room> getFreeRoomsSorted(LocalDate specificDate, SortEnum sortEnum) {
        String fieldToSort;

        switch (sortEnum) {
            case BY_ADDITION: fieldToSort = "id"; break;
            case BY_PRICE: fieldToSort = "price"; break;
            case BY_CAPACITY:  fieldToSort = "capacity"; break;
            case BY_STARS: fieldToSort = "starsNumber"; break;
            default: throw new NoSuchElementException("Such sortEnum does not exist");
        }

        return getDefaultDao().getFreeRoomsByDateSorted(specificDate, fieldToSort);
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
    public List<Room> getFree() {
        return roomDao.getFreeRoomsNowSorted("id");
    }

    @Override
    public List<Room> getFree(LocalDate asAtSpecificDate) {
        return getFreeRoomsSorted(asAtSpecificDate, SortEnum.BY_ADDITION);
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
