package com.senla.dao.impl;

import com.senla.dao.RoomDao;
import com.senla.model.Guest;
import com.senla.model.Room;
import com.senla.model.RoomStatus;
import com.senla.model.Room_;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class RoomDaoImpl extends AbstractDaoImpl<Room> implements RoomDao {

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
    protected Class<Room> daoEntityClass() {
        return Room.class;
    }

    private List<Room> getFreeRoomsNowSorted(String fieldToSortBy, String order) {
        TypedQuery<Room> q = entityManager.createQuery(
                "select r from Room r where roomStatus = :roomStatus order by :fieldToSortBy " + order, Room.class
        );
        q.setParameter("roomStatus", RoomStatus.FREE);
        q.setParameter("fieldToSortBy", fieldToSortBy);
        return q.getResultList();
    }

    @Override
    public List<Room> getFreeRoomsByDateSorted(LocalDate asAtSpecificDate, String fieldToSortBy, String order) {
        if (Objects.equals(asAtSpecificDate, LocalDate.now())) {
            return getFreeRoomsNowSorted(fieldToSortBy, order);
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
        Root<Room> room = cq.from(Room.class);

        Predicate predicate = getPredicateForFreeRoomsOnDate(asAtSpecificDate, room, cb);

        List<Order> orderList = getOrderList(order, fieldToSortBy, cb, room);

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
