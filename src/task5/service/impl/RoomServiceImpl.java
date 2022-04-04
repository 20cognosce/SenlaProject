package task5.service.impl;

import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;
import task5.dao.model.Guest;
import task5.dao.model.Room;
import task5.dao.model.RoomStatus;
import task5.service.RoomService;

import java.time.LocalDate;
import java.util.*;


public class RoomServiceImpl extends AbstractServiceImpl  implements RoomService {
    public RoomServiceImpl (GuestDao guestDao, RoomDao roomDao, MaintenanceDao maintenanceDao) {
        super(guestDao, roomDao, maintenanceDao);
    }

    @Override
    public List<Guest> getLastNGuests(int roomId, int N) throws NoSuchElementException {
        List<Guest> allTimeList = roomDao.getRoomById(roomId).getGuestsAllTimeList();
        int lastGuestIndex = allTimeList.size() - 1;
        ArrayList<Guest> out = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            try {
                out.add(allTimeList.get(lastGuestIndex - i));
            } catch (Exception e) {
                break;
            }
        }
        return out;
    }

    @Override
    public void createRoom(String name, int capacity, int starsNumber, RoomStatus roomStatus, int price) {
        roomDao.createRoom(name, capacity, starsNumber, roomStatus, price);
    }

    @Override
    public List<Room> getAll() {
        return roomDao.getAll();
    }

    @Override
    public String getAllAsString() {
        return roomDao.getAllAsString();
    }

    @Override
    public String getAsString(List<Room> subList) {
        return roomDao.getAsString(subList);
    }

    @Override
    public Room getRoomById(int id) throws NoSuchElementException {
        return roomDao.getRoomById(id);
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
    public List<Room> getSorted(List<Room> roomsListToSort, Comparator<Room> comparator) {
        return roomDao.getSorted(roomsListToSort, comparator);
    }

    @Override
    public String getGuestsListAsString(int roomId) throws NoSuchElementException {
        return roomDao.getGuestsListAsString(roomId);
    }
}
