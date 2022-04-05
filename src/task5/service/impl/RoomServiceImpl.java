package task5.service.impl;

import task5.controller.action.SortEnum;
import task5.dao.AbstractDao;
import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;
import task5.dao.model.Guest;
import task5.dao.model.Room;
import task5.dao.model.RoomStatus;
import task5.service.RoomService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class RoomServiceImpl extends AbstractServiceImpl<Room, RoomDao> implements RoomService {
    public RoomServiceImpl (GuestDao guestDao, RoomDao roomDao, MaintenanceDao maintenanceDao) {
        super(roomDao, guestDao, roomDao, maintenanceDao);
    }

    @Override
    public List<Guest> getLastNGuests(int roomId, int N) throws NoSuchElementException {
        List<Guest> allTimeList = roomDao.getById(roomId).getGuestsAllTimeList();
        return allTimeList.stream().sorted(Comparator.comparing(Guest::getCheckInDate).reversed()).limit(N).collect(Collectors.toList());
    }

    @Override
    public List<Guest> getGuestsList(int roomId) throws NoSuchElementException {
        return getById(roomId).getGuestsCurrentList();
    }

    @Override
    public void createRoom(String name, int capacity, int starsNumber, RoomStatus roomStatus, int price) {
        roomDao.createRoom(name, capacity, starsNumber, roomStatus, price);
    }

    @Override
    public List<Room> getFree() {
        return roomDao.getFree();
    }

    @Override
    public List<Room> getFree(LocalDate asAtSpecificDate) {
        return roomDao.getFree(asAtSpecificDate);
    }


    @Override
    public void setPrice(int roomId, int price) throws NoSuchElementException {
        roomDao.getById(roomId).setPrice(price);
    }

    @Override
    public void setStatus(int roomId, RoomStatus roomStatus) {
        roomDao.getById(roomId).setRoomStatus(roomStatus);
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
}
