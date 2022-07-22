package com.senla.dao.impl;

import com.senla.dao.RoomDao;
import com.senla.model.Guest;
import com.senla.model.Room;
import com.senla.model.RoomStatus;
import com.senla.model.Room_;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@NoArgsConstructor
public class RoomDaoImpl extends AbstractDaoImpl<Room> implements RoomDao {

    @Override
    public void updateRoomStatus(Room room, RoomStatus roomStatus) {
        room.setRoomStatus(roomStatus);
        update(room);
    }

    @Override
    public void updateRoomPrice(Room room, int price) {
        room.setPrice(price);
        update(room);
    }

    @Override
    public void removeGuest(Room room, Guest guest) {
        room.removeGuest(guest);
        update(room);
    }

    @Override
    public void addGuestToRoom(Room room, Guest guest) {
        room.addGuest(guest);
        update(room);
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
    protected Class<Room> daoEntityClass() {
        return Room.class;
    }

    @Override
    public List<Guest> getGuestsOfRoom(Room room) {
        /*TypedQuery<Room> q = entityManager.createQuery(
                "SELECT r FROM Room r JOIN FETCH r.currentGuestList WHERE r.id = :roomId", Room.class
        );
        q.setParameter("roomId", roomId);*/

        TypedQuery<Guest> q = entityManager.createQuery(
                "SELECT g FROM Guest g where g.room = :room", Guest.class
        );
        q.setParameter("room", room);
        return q.getResultList();
    }

    private List<Room> getFreeRoomsNowSorted(String fieldToSortBy) {
        TypedQuery<Room> q = entityManager.createQuery(
                "select r from Room r where roomStatus = :roomStatus order by :fieldToSortBy", Room.class
        );
        q.setParameter("roomStatus", RoomStatus.FREE);
        q.setParameter("fieldToSortBy", fieldToSortBy);
        return q.getResultList();
    }

    @Override
    public List<Room> getFreeRoomsByDateSorted(LocalDate asAtSpecificDate, String fieldToSortBy) {
        if (Objects.equals(asAtSpecificDate, LocalDate.now())) {
            return getFreeRoomsNowSorted(fieldToSortBy);
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
        Root<Room> room = cq.from(Room.class);

        Predicate predicate = getPredicateForFreeRoomsOnDate(asAtSpecificDate, room, cb);

        List<Order> orderList = new ArrayList<>();
        orderList.add(cb.asc(room.get(fieldToSortBy)));

        TypedQuery<Room> query = entityManager.createQuery(cq
                .select(room)
                .where(predicate)
                .orderBy(orderList)
                .distinct(true));

        return query.getResultList();
    }

    @Override
    public Long getFreeRoomsAmount(LocalDate asAtSpecificDate) {
        if (Objects.equals(asAtSpecificDate, LocalDate.now())) {
            TypedQuery<Long> q = entityManager.createQuery(
                    "SELECT count (*) FROM Room r where r.roomStatus = :FREE", Long.class
            );
            q.setParameter("FREE", RoomStatus.FREE);
            return q.getSingleResult();
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Room> room = cq.from(Room.class);
        Predicate predicate = getPredicateForFreeRoomsOnDate(asAtSpecificDate, room, cb);

        TypedQuery<Long> query = entityManager.createQuery(cq
                .select(cb.countDistinct(room))
                .where(predicate));

        return query.getSingleResult();
    }

    private Predicate getPredicateForFreeRoomsOnDate(LocalDate date, Root<Room> room, CriteriaBuilder cb) {
        Join<Room, Guest> roomGuestJoin = room.join(Room_.currentGuestList);
        //ListJoin<Room, Guest> roomGuestJoin = root.joinList("currentGuestList");
        Predicate predicateCheckInIsAfter = cb.greaterThan(roomGuestJoin.get("checkInDate"), date);
        Predicate predicateCheckOutIsBefore = cb.lessThan(roomGuestJoin.get("checkOutDate"), date);

        return cb.or(predicateCheckInIsAfter, predicateCheckOutIsBefore);
    }
}
