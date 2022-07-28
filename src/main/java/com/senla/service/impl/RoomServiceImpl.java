package com.senla.service.impl;

import com.senla.build.config.SortEnum;
import com.senla.dao.RoomDao;
import com.senla.model.Guest;
import com.senla.model.Room;
import com.senla.model.Room_;
import com.senla.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.ServiceUnavailableException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl extends AbstractServiceImpl<Room, RoomDao> implements RoomService {

    private final RoomDao roomDao;
    @Value("${change.room.status.possibility}")
    String changeRoomStatusPossibility;
    @Value("${last.n.guests}") //я не могу придумать семантику лучше для этой переменной
    int lastNGuests;

    @Override
    public List<Guest> getLastNGuests(long roomId) throws NoSuchElementException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
        Root<Room> root = cq.from(Room.class);
        root.fetch("currentGuestList", JoinType.LEFT);

        List<Guest> currentGuestList = entityManager.createQuery(cq
                        .select(root)
                        .where(cb.equal(root.get(Room_.id), roomId))
                )
                .getSingleResult()
                .getCurrentGuestList();

        //В комнате одновременно не будет тысяч гостей, так что пусть останется компаратор...
        return currentGuestList.stream().sorted(
                Comparator.comparing(Guest::getCheckInDate).reversed()).limit(lastNGuests).collect(Collectors.toList());
    }

    @Override
    public List<Guest> getGuestsList(long roomId) throws NoSuchElementException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
        Root<Room> root = cq.from(Room.class);
        root.fetch("currentGuestList", JoinType.LEFT);

        return entityManager.createQuery(cq
                        .select(root)
                        .where(cb.equal(root.get(Room_.id), roomId))
                )
                .getSingleResult()
                .getCurrentGuestList();
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
            throw new ServiceUnavailableException("Опция недоступна");
        }
        roomDao.update(updatedRoom);
    }

    @Override
    @Transactional
    public void updateRoomDetails(long roomId, String details) {
        roomDao.getById(roomId).setDetails(details);
    }

    public List<Room> getRoomsSorted(SortEnum sortEnum, String order) {
        String fieldToSort;

        switch (sortEnum) {
            case BY_ADDITION: fieldToSort = "id"; break;
            case BY_PRICE: fieldToSort = "price"; break;
            case BY_CAPACITY:  fieldToSort = "capacity"; break;
            case BY_STARS: fieldToSort = "starsNumber"; break;
            default: throw new NoSuchElementException("Such sortEnum does not exist");
        }

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
