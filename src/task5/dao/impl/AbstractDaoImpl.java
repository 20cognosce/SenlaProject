package task5.dao.impl;

import task5.dao.AbstractDao;
import task5.dao.model.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public abstract class AbstractDaoImpl<T extends AbstractEntity> implements AbstractDao<T> {
    private final List<T> repository = new ArrayList<>();
    private final IdSupplier idSupplier = new IdSupplier();

    @Override
    public List<T> getAll() {
        return new ArrayList<>(repository);
    }

    @Override
    public T getById(long id) throws NoSuchElementException {
        return repository.stream()
                .filter(element -> (element.getId() == id))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public T getByName(String name) throws NoSuchElementException {
        return repository.stream()
                .filter(element -> (name.equals(element.getName())))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<T> getSorted(List<T> listToSort, Comparator<T> comparator) {
        return listToSort.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public void addToRepo(T element) throws KeyAlreadyExistsException {
        if (repository.contains(element)) {
            throw new KeyAlreadyExistsException();
        }
        repository.add(element);
    }

    @Override
    public void deleteFromRepo(T element) throws NoSuchElementException {
        if(!repository.remove(element)) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public long supplyId() {
        return idSupplier.supplyId();
    }

    @Override
    public String convertDataToExportFormat(T element) throws ClassNotFoundException {
        if (element instanceof Guest) {
            Guest guest = (Guest) element;
            StringBuilder line = new StringBuilder();
            long roomId;
            try {
                roomId = guest.getRoom().getId();
            } catch (NullPointerException e) {
                roomId = 0L;
            }
            line.append(guest.getId()).append(",")
                    .append(guest.getName()).append(",")
                    .append(guest.getPassport()).append(",")
                    .append(guest.getCheckInDate()).append(",")
                    .append(guest.getCheckOutDate()).append(",")
                    .append(roomId);
            return line.toString();
        }

        if (element instanceof Room) {
            Room room = (Room) element;
            return room.getId() + "," +
                    room.getName() + "," +
                    room.getCapacity() + "," +
                    room.getStarsNumber() + "," +
                    room.getRoomStatus() + "," +
                    room.getPrice();
        }

        if (element instanceof Maintenance) {
            Maintenance maintenance = (Maintenance) element;
            return maintenance.getId() + "," +
                    maintenance.getName() + "," +
                    maintenance.getPrice() + "," +
                    maintenance.getCategory();
        }

        throw new ClassNotFoundException();
    }
}
