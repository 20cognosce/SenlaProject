package com.senla.service.impl;

import com.senla.build.config.SortEnum;
import com.senla.dao.RoomDao;
import com.senla.model.Guest;
import com.senla.model.Room;
import com.senla.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl extends AbstractServiceImpl<Room, RoomDao> implements RoomService {

    private final RoomDao roomDao;
    @Value("${change.room.status.possibility}")
    String changeRoomStatusPossibility;
    @Value("${last.n.guests}") //я не могу придумать семантику лучше для этой переменной
    int lastNGuests;

    @Override
    public List<Guest> getLastNGuests(long roomId) throws NoSuchElementException {
        Room room = getById(roomId);
        List<Guest> currentGuestList = roomDao.getGuestsOfRoom(room);
        return currentGuestList.stream().sorted(
                Comparator.comparing(Guest::getCheckInDate).reversed()).limit(lastNGuests).collect(Collectors.toList());
    }

    @Override
    public List<Guest> getGuestsList(long roomId) throws NoSuchElementException {
        Room room = getById(roomId);
        return roomDao.getGuestsOfRoom(room);
    }

    @Override
    @Transactional
    public void createRoom(Room room) {
        roomDao.create(room);
    }

    @Override
    @Transactional
    public void updateRoom(Room updatedRoom) throws ServiceUnavailableException {
        Room detachedRoom = roomDao.getById(updatedRoom.getId());
        if ("no".equals(changeRoomStatusPossibility) && (detachedRoom.getRoomStatus() != updatedRoom.getRoomStatus())) {
            throw new ServiceUnavailableException("Опция недоступна");
        }
        roomDao.update(updatedRoom);
    }

    @Override
    @Transactional
    public void updateRoomDetails(long roomId, String details) {
        roomDao.getById(roomId).setDetails(details);
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
    public List<Room> getFree(LocalDate asAtSpecificDate) {
        return getFreeRoomsSorted(asAtSpecificDate, SortEnum.BY_ADDITION);
    }

    @Override
    public Long getFreeAmount(LocalDate asAtSpecificDate) {
        return roomDao.getFreeRoomsAmount(asAtSpecificDate);
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
    public RoomDao getDefaultDao() {
        return roomDao;
    }
}
