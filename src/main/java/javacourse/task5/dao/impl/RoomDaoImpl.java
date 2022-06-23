package javacourse.task5.dao.impl;

import javacourse.task5.build.factory.Component;
import javacourse.task5.build.orm.OrmManagementUtil;
import javacourse.task5.dao.GuestDao;
import javacourse.task5.dao.RoomDao;
import javacourse.task5.dao.entity.Guest;
import javacourse.task5.dao.entity.Room;
import javacourse.task5.dao.entity.RoomStatus;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RoomDaoImpl extends AbstractDaoImpl<Room> implements RoomDao {
    private static final Logger logger = LoggerFactory.getLogger(RoomDaoImpl.class);
    public RoomDaoImpl() {
        super();
    }

    @Override
    public List<Room> getFree() {
        List<Room> freeRooms = new ArrayList<>();
        getAll().forEach((room) -> {
            if (room.getRoomStatus() == RoomStatus.FREE) freeRooms.add(room);
        });
        return freeRooms;
    }

    @Override
    public List<Room> getFree(LocalDate asAtSpecificDate, GuestDao guestDao) {
        if (Objects.equals(asAtSpecificDate, LocalDate.now())) {
            return getFree();
        }

        List<Room> freeRooms = new ArrayList<>();
        getAll().forEach(room -> {
            final boolean[] isFree = {true};

            if (room.getCurrentGuestIdList().isEmpty()) {
                if (room.getRoomStatus() == RoomStatus.FREE) {
                    freeRooms.add(room);
                }
                return;
            }

            room.getCurrentGuestIdList().forEach(guestId -> {
                Guest guest = guestDao.getById(guestId);
                if (!guest.getCheckInDate().isAfter(asAtSpecificDate) &&
                        guest.getCheckOutDate().isAfter(asAtSpecificDate)) isFree[0] = false;
            });
            if (isFree[0]) {
                freeRooms.add(room);
            }
        });

        return freeRooms;
    }

    @Override
    public void updateRoomStatus(long roomId, RoomStatus roomStatus) {
        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            session.beginTransaction();
            Room room = getById(roomId);
            room.setRoomStatus(roomStatus);
            session.update(room);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void updateRoomPrice(long roomId, int price) {
        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            session.beginTransaction();
            Room room = getById(roomId);
            room.setPrice(price);
            session.update(room);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void removeGuest(long roomId, long guestId) {
        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            session.beginTransaction();
            Room room = getById(roomId);
            room.removeGuest(guestId);
            session.update(room);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void addGuestToRoom(long roomId, long guestId) {
        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            session.beginTransaction();
            Room room = getById(roomId);
            room.addGuest(guestId);
            session.update(room);
            /*LocalDate checkInDate = guest.getCheckInDate();
            LocalDate checkOutDate = guest.getCheckOutDate();
            Query query = session.createSQLQuery("" +
                    "INSERT INTO room_guest (room_id, guest_id, check_in_date, check_out_date)" +
                    "VALUES (:roomId, :guestId, $checkInDate, $checkOutDate)");
            query.executeUpdate();*/
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public String exportData(Room room) {
        return room.getId() + "," +
                room.getName() + "," +
                room.getCapacity() + "," +
                room.getStarsNumber() + "," +
                room.getRoomStatus() + "," +
                room.getPrice();
    }

    @Override
    public Room getDaoEntity() {
        return new Room();
    }
}
