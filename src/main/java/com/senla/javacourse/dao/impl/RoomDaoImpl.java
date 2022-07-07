package com.senla.javacourse.dao.impl;


import com.senla.javacourse.dao.RoomDao;
import com.senla.javacourse.dao.entity.Guest;
import com.senla.javacourse.dao.entity.Room;
import com.senla.javacourse.dao.entity.RoomStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Repository
public class RoomDaoImpl extends AbstractDaoImpl<Room> implements RoomDao {
    @PersistenceContext
    private EntityManager entityManager;

    public RoomDaoImpl() {
        super();
    }

    @Override
    public List<Room> getAll(String fieldToSortBy) {
        /*TypedQuery<Room> q = entityManager.createQuery(
                "select r from Room r order by :fieldToSortBy", Room.class
        );
        q.setParameter("fieldToSortBy", fieldToSortBy);
        return q.getResultList();*/

        String str = "select r from Room r order by " + fieldToSortBy + " asc";
        TypedQuery<Room> q = entityManager.createQuery(str, Room.class);
        return q.getResultList();
    }

    @Override
    public Room getById(long id) {
        Room room = entityManager.find(Room.class, id);
        if (Objects.isNull(room)) {
            throw new NoSuchElementException("Room not found");
        } else {
            return room;
        }
    }

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
    public List<Room> getFreeRoomsNowSorted(String fieldToSortBy) {
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

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
        Root<Room> root = criteriaQuery.from(Room.class);

        ListJoin<Room, Guest> currentGuestList = root.joinList("currentGuestList");
        Predicate predicateCheckInIsAfter = criteriaBuilder.greaterThan(currentGuestList.get("checkInDate"), asAtSpecificDate);
        Predicate predicateCheckOutIsBefore = criteriaBuilder.lessThan(currentGuestList.get("checkOutDate"),asAtSpecificDate);
        Predicate predicate = criteriaBuilder.or(predicateCheckInIsAfter, predicateCheckOutIsBefore);

        List<Order> orderList = new ArrayList<>();
        orderList.add(criteriaBuilder.asc(root.get(fieldToSortBy)));
        TypedQuery<Room> query = entityManager.createQuery(criteriaQuery
                .select(root)
                .where(predicate)
                .orderBy(orderList)
                .distinct(true));

        return query.getResultList();
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
}
