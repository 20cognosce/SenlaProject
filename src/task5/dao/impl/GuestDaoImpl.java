package task5.dao.impl;

import task5.dao.GuestDao;
import task5.dao.model.Guest;
import task5.dao.model.IdSupplier;
import task5.dao.model.Room;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDate;
import java.util.*;

public class GuestDaoImpl implements GuestDao {
    private final IdSupplier idSupplier = new IdSupplier();
    private final List<Guest> repository = new ArrayList<>();

    @Override
    public void createGuest(String fullName, String passport, LocalDate checkInTime, LocalDate checkOutTime, Room room) {
        Guest newGuest = new Guest(idSupplier.supplyId(), fullName, passport, checkInTime, checkOutTime, room);
        if (repository.contains(newGuest)) {
            throw new KeyAlreadyExistsException("Guest already exists");
        }
        repository.add(newGuest);
    }

    @Override
    public void addGuestToRoom(Guest guest, Room room) {
        if (!repository.contains(guest)) {
            repository.add(guest);
        }
        room.addGuest(guest);
        guest.setRoom(room);
    }

    @Override
    public List<Guest> getAll() {
        return new ArrayList<>(repository);
    }

    @Override
    public Guest getGuestById(int id) throws NoSuchElementException {
        return repository.stream()
                .filter(guest -> (guest.getId() == id))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Guest getGuestByName(String fullName) throws NoSuchElementException {
        return repository.stream()
                .filter(guest -> (fullName.equals(guest.getName())))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public String getAsString(List<Guest> subList) {
        StringBuilder out = new StringBuilder();
        subList.forEach(guest -> out.append(guest.toString()));
        return out.toString();
    }

    @Override
    public void removeGuestFromRoom(Guest guest) throws NullPointerException, NoSuchElementException {
        guest.getRoom().removeGuest(guest);
        guest.setRoom(null);
    }

    @Override
    public void deleteGuest(Guest guest) throws NullPointerException, NoSuchElementException {
        removeGuestFromRoom(guest);
        if(!repository.remove(guest)) {
            throw new NoSuchElementException();
        };
    }

    @Override
    public List<Guest> getSorted(List<Guest> listToSort, Comparator<Guest> comparator) {
        List<Guest> sorted = new ArrayList<>();
        listToSort.stream().sorted(comparator)
                .forEach(sorted::add);
        return sorted;
    }
}
