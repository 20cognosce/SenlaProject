package task5.service.impl;

import task5.dao.GuestDao;
import task5.dao.MaintenanceDao;
import task5.dao.RoomDao;
import task5.dao.model.Guest;
import task5.dao.model.Room;
import task5.dao.model.RoomStatus;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.time.LocalDate;
import java.util.*;


public class RoomServiceImpl extends AbstractServiceImpl  implements RoomService {
    GuestService guestService;
    MaintenanceService maintenanceService;
    public RoomServiceImpl (GuestDao guestDao, RoomDao roomDao, MaintenanceDao maintenanceDao) {
        super(guestDao, roomDao, maintenanceDao);
    }

    @Override
    public List<Guest> getLastNGuests(Room room, int N) {
        List<Guest> allTimeList = room.getGuestsAllTimeList();
        int lastGuestIndex =allTimeList.size() - 1;
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
    public Room createRoom(int roomNumber, int guestsMaxNumber, int STARS_NUMBER, RoomStatus roomCurrentStatus, int price) {
        return null;
    }

    @Override
    public void addRoom(Room room) {

    }

    @Override
    public List<Room> getAll() {
        return null;
    }

    @Override
    public String getAllAsString() {
        return roomDao.getAllAsString();
    }

    @Override
    public String getAllAsString(List<Room> subList) {
        return roomDao.getAllAsString(subList);
    }

    @Override
    public Room getRoomById(int id) throws NoSuchElementException {
        return roomDao.getRoomById(id);
    }

    @Override
    public List<Room> getFree() {
        return null;
    }

    @Override
    public List<Room> getFree(LocalDate asAtSpecificDate) {
        return null;
    }

    @Override
    public List<Room> getSorted(List<Room> roomsListToSort, Comparator<Room> comparator) {
        return roomDao.getSorted(roomsListToSort, comparator);
    }
}
