package javacourse.task5.dao;

import javacourse.task5.dao.entity.Guest;
import javacourse.task5.dao.entity.Room;

import java.util.List;
import java.util.NoSuchElementException;

public interface GuestDao extends AbstractDao<Guest> {
    void updatePrice(Guest guest, Room room);
    void addToArchivedRepository(Guest guest) throws CloneNotSupportedException;
    Guest getFromArchivedRepositoryById(long id) throws NoSuchElementException;
    List<Guest> getArchivedAll();
}
