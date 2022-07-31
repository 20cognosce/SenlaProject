package com.senla.service.impl;

import com.senla.dao.RoomDao;
import com.senla.model.Guest;
import com.senla.model.Room;
import com.senla.service.RoomService;
import com.senla.util.SortEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RoomServiceImpl extends AbstractServiceImpl<Room, RoomDao> implements RoomService {

    private final RoomDao roomDao;
    @Value("${change.room.status.possibility}")
    String changeRoomStatusPossibility;
    @Value("${last.n.guests}") //я не могу придумать семантику лучше для этой переменной
    int lastNGuests;

    @Override
    public List<Guest> getLastNGuests(long roomId) throws NoSuchElementException {
        List<Guest> currentGuestList = getDefaultDao().getGuestList(roomId);

        //В комнате одновременно не будет тысяч гостей, так что пусть останется компаратор...
        return currentGuestList.stream().sorted(
                Comparator.comparing(Guest::getCheckInDate).reversed()).limit(lastNGuests).collect(Collectors.toList());
    }

    @Override
    public List<Guest> getGuestsList(long roomId) throws NoSuchElementException {
        return getDefaultDao().getGuestList(roomId);
    }

    @Transactional
    @Override
    public void createRoom(Room room) {
        roomDao.create(room);
    }

    @Transactional
    @Override
    public void updateRoom(Room updatedRoom) throws ServiceUnavailableException {
        Room detachedRoom = roomDao.getById(updatedRoom.getId());
        if ("no".equals(changeRoomStatusPossibility) && (detachedRoom.getRoomStatus() != updatedRoom.getRoomStatus())) {
            ServiceUnavailableException e = new ServiceUnavailableException("Опция недоступна");
            log.error(e.getLocalizedMessage(), e);
            throw e;
        }
        roomDao.update(updatedRoom);
    }

    @Override
    @Transactional
    public void updateRoomDetails(long roomId, String details) {
        roomDao.getById(roomId).setDetails(details);
    }

    public List<Room> getRoomsSorted(SortEnum sortEnum, String order) {
        String fieldToSort = getFieldToSortFromEnum(sortEnum);
        return this.getAll(fieldToSort, order);
    }

    public List<Room> getFreeRoomsSorted(LocalDate specificDate, SortEnum sortEnum, String order) {
        String fieldToSort = getFieldToSortFromEnum(sortEnum);
        return getDefaultDao().getFreeRoomsByDateSorted(specificDate, fieldToSort, order);
    }

    @Override
    public List<Room> sortByAddition(String order) {
        return getRoomsSorted(SortEnum.BY_ADDITION, order);
    }

    @Override
    public List<Room> sortByCapacity(String order) {
        return getRoomsSorted(SortEnum.BY_CAPACITY, order);
    }

    @Override
    public List<Room> sortByPrice(String order) {
        return getRoomsSorted(SortEnum.BY_PRICE, order);
    }

    @Override
    public List<Room> sortByStars(String order) {
        return getRoomsSorted(SortEnum.BY_STARS, order);
    }

    @Override
    public List<Room> getFree(LocalDate asAtSpecificDate, String order) {
        return getFreeRoomsSorted(asAtSpecificDate, SortEnum.BY_ADDITION, order);
    }

    @Override
    public Long getFreeAmount(LocalDate asAtSpecificDate) {
        return roomDao.getFreeRoomsAmount(asAtSpecificDate);
    }

    @Override
    public List<Room> sortFreeRoomsByAddition(LocalDate specificDate, String order) {
        return getFreeRoomsSorted(specificDate, SortEnum.BY_CAPACITY, order);
    }

    @Override
    public List<Room> sortFreeRoomsByCapacity(LocalDate specificDate, String order) {
        return getFreeRoomsSorted(specificDate, SortEnum.BY_CAPACITY, order);
    }

    @Override
    public List<Room> sortFreeRoomsByPrice(LocalDate specificDate, String order) {
        return getFreeRoomsSorted(specificDate, SortEnum.BY_PRICE, order);
    }

    @Override
    public List<Room> sortFreeRoomsByStars(LocalDate specificDate, String order) {
        return getFreeRoomsSorted(specificDate, SortEnum.BY_STARS, order);
    }

    @Override
    public RoomDao getDefaultDao() {
        return roomDao;
    }
}
