package javacourse.task5.service.impl;

import javacourse.task5.build.factory.Component;
import javacourse.task5.controller.action.SortEnum;
import javacourse.task5.dao.GuestDao;
import javacourse.task5.dao.entity.Guest;
import javacourse.task5.dao.entity.Room;
import javacourse.task5.service.GuestService;

import javax.management.InvalidAttributeValueException;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;


@Component
public class GuestServiceImpl extends AbstractServiceImpl<Guest, GuestDao> implements GuestService {
    public GuestServiceImpl() {
        super();
    }

    @Override
    public void createGuest(String fullName, String passport,
                            LocalDate checkInTime, LocalDate checkOutTime,
                            long roomId) throws KeyAlreadyExistsException {
        try {
            if (roomId != 0) roomDao.getById(roomId);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return;
        }
        Long objectRoomId;
        if (roomId == 0) {
            objectRoomId = null;
        } else {
            objectRoomId = roomId;
        }
        Guest guest = new Guest(fullName, passport, checkInTime, checkOutTime, objectRoomId);
        guestDao.addToRepo(guest);
        if (roomId != 0) guestDao.updatePrice(guest.getId(), roomDao.getById(roomId).getPrice());
    }


    @Override
    public void addGuestToRoom(long guestId, long roomId) {

        try {
            Guest guest = guestDao.getById(guestId);
            Room room = roomDao.getById(roomId);
            if (room.isUnavailableToSettle()) throw new RuntimeException("Room is unavailable");
            if (!Objects.isNull(guest.getRoomId())) removeGuestFromRoom(guestId);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //Как это добавить в одну транзакцию?
        roomDao.addGuestToRoom(roomId, guestId);
        guestDao.updateRoomId(guestId, roomId);
        Room roomUpdated = roomDao.getById(roomId);
        if (roomUpdated.getCurrentGuestIdList().size() == 1) {
            //pay only the first settled after the room was empty
            guestDao.updatePrice(guestId, roomUpdated.getPrice());
        }
        //careful, getId return different objects each time.
        //after updating it in the previous methods we must call getId again for update
    }

    @Override
    public void removeGuestFromRoom(long guestId) {
        Guest guest;
        try {
            guest = guestDao.getById(guestId);
            if (Objects.isNull(guest.getRoomId())) {
                return;
            }
            roomDao.removeGuest(guest.getRoomId(), guestId);
            guestDao.updateRoomId(guestId, 0L);
            guestDao.updatePrice(guestId, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGuest(long guestId) {
        try {
            removeGuestFromRoom(guestId);
            guestDao.deleteFromRepo(guestDao.getById(guestId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Guest> getSorted(List<Guest> listToSort, SortEnum sortBy) throws NoSuchElementException {
        switch (sortBy) {
            case BY_ADDITION: return getDefaultDao().getSorted(listToSort, Comparator.comparingLong(Guest::getId));
            case BY_PRICE: return getDefaultDao().getSorted(listToSort, Comparator.comparingInt(Guest::getPrice));
            case BY_ALPHABET: return getDefaultDao().getSorted(listToSort, Comparator.comparing(Guest::getName));
            case BY_CHECKOUT_DATE: return getDefaultDao().getSorted(listToSort, Comparator.comparing(Guest::getCheckOutDate));
        }
        throw new NoSuchElementException();
    }

    @Override
    public Integer getAllAmount() {
        return guestDao.getAll().size();
    }

    @Override
    public Integer getPriceByGuest(long guestId) {
        return getById(guestId).getPrice();
    }

    @Override
    public List<Guest> sortByAddition() {
        return getSorted(getAll(), SortEnum.BY_ADDITION);
    }

    @Override
    public List<Guest> sortByAlphabet() {
        return getSorted(getAll(), SortEnum.BY_ALPHABET);
    }

    @Override
    public List<Guest> sortByCheckOutDate() {
        return getSorted(getAll(), SortEnum.BY_CHECKOUT_DATE);
    }

    @Override
    public void importData(List<List<String>> records) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        records.forEach(entry -> {
            long guestId;
            String name;
            String passport;
            LocalDate checkInDate;
            LocalDate checkOutDate;
            long roomId;

            try {
                guestId = Long.parseLong(entry.get(0));
                name = entry.get(1);
                passport = entry.get(2);
                checkInDate = LocalDate.parse(entry.get(3), dtf);
                checkOutDate = LocalDate.parse(entry.get(4), dtf);
                roomId = Long.parseLong(entry.get(5));
            } catch (Exception e) {
                System.out.println(e.getClass().getCanonicalName() + ": "  + e.getMessage());
                return;
            }

            //If room's check fails the entire item fails
            if (roomId != 0) {
                try {
                    if (roomDao.getById(roomId).isUnavailableToSettle())
                        throw new InvalidAttributeValueException("Room is unavailable");
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }

            try {
                Guest guest = getById(guestId);
                guest.setName(name);
                guest.setPassport(passport);
                guest.setCheckInDate(checkInDate);
                guest.setCheckOutDate(checkOutDate);
                if (roomId == 0) {
                    removeGuestFromRoom(guestId);
                } else {
                    addGuestToRoom(guestId, roomId);
                }
            } catch (NoSuchElementException e) {
                createGuest(name, passport, checkInDate, checkOutDate, 0);
                if (roomId != 0) addGuestToRoom(guestId, roomId);
            }
        });
    }

    @Override
    public String getExportTitleLine() {
        return "id,Name,Passport,CheckInDate [dd.MM.yyyy],CheckOutDate [dd.MM.yyyy],roomId";
    }

    @Override
    public String exportData(long id) throws NoSuchElementException {
        return getDefaultDao().exportData(getById(id));
    }

    @Override
    public GuestDao getDefaultDao() {
        return guestDao;
    }
}
