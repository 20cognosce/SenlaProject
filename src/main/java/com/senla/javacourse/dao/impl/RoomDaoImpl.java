package com.senla.javacourse.dao.impl;


import com.senla.javacourse.dao.entity.Room;
import com.senla.javacourse.dao.RoomDao;
import com.senla.javacourse.dao.entity.Guest;
import com.senla.javacourse.dao.entity.RoomStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class RoomDaoImpl extends AbstractDaoImpl<Room> implements RoomDao {
    public RoomDaoImpl() {
        super();
    }

    @Override
    public List<Room> getFree() {
        var ref = new Object() {
            List<Room> freeRooms;
        };
        openSessionAndExecuteTransactionTask(((session, criteriaBuilder) -> {
            CriteriaQuery<Room> criteria = criteriaBuilder.createQuery(Room.class);
            Root<Room> root = criteria.from(Room.class);
            criteria.select(root).where(criteriaBuilder.equal(root.get("roomStatus"), RoomStatus.FREE));
            ref.freeRooms = session.createQuery(criteria).getResultList();
        }));
        return ref.freeRooms;
    }

    @Override
    public List<Room> getFree(LocalDate asAtSpecificDate, String fieldNameToSortBy) {
        if (Objects.equals(asAtSpecificDate, LocalDate.now())) {
            return getFree();
        }

        /*List<Room> freeRooms = getFree();
        //TODO: Ну тут без getAll вообще никак, такой навороченный предикат вне моих способностей
        getAll().forEach(room -> {
            room.getCurrentGuestList().forEach(guest -> {
                if (guest.getCheckInDate().isAfter(asAtSpecificDate) || guest.getCheckOutDate().isBefore(asAtSpecificDate)) {
                    freeRooms.add(room);
                }
            });
        });*/

        var ref = new Object() {
            List<Room> list;
        };

        //Это сумасшедший предикат
        openSessionAndExecuteTransactionTask((session, criteriaBuilder) -> {
            CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
            Root<Room> root = criteriaQuery.from(Room.class);

            ListJoin<Room, Guest> currentGuestList = root.joinList("currentGuestList");
            Predicate predicateCheckInIsAfter = criteriaBuilder.greaterThan(currentGuestList.get("checkInDate"), asAtSpecificDate);
            Predicate predicateCheckOutIsBefore = criteriaBuilder.lessThan(currentGuestList.get("checkOutDate"),asAtSpecificDate);
            Predicate predicate = criteriaBuilder.or(predicateCheckInIsAfter, predicateCheckOutIsBefore);

            List<Order> orderList = new ArrayList<>();
            orderList.add(criteriaBuilder.asc(root.get(fieldNameToSortBy)));
            TypedQuery<Room> query = session.createQuery(criteriaQuery
                    .select(root)
                    .where(predicate)
                    .orderBy(orderList)
                    .distinct(true));

            ref.list = query.getResultList();
        });

        return ref.list;
    }

    @Override
    public void updateRoomStatus(Room room, RoomStatus roomStatus) {
        openSessionAndExecuteTransactionTask(((session, criteriaBuilder) -> {
            room.setRoomStatus(roomStatus);
            session.update(room);
        }));
    }

    @Override
    public void updateRoomPrice(Room room, int price) {
        openSessionAndExecuteTransactionTask(((session, criteriaBuilder) -> {
            room.setPrice(price);
            session.update(room);
        }));
    }

    @Override
    public void removeGuest(Room room, Guest guest) {
        openSessionAndExecuteTransactionTask(((session, criteriaBuilder) -> {
            room.removeGuest(guest);
            session.update(room);
        }));
    }

    @Override
    public void addGuestToRoom(Room room, Guest guest) {
        openSessionAndExecuteTransactionTask(((session, criteriaBuilder) -> {
            room.addGuest(guest);
            session.update(room);
        }));
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
