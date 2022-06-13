package javacourse.task5.dao.impl;

import javacourse.task5.build.config.ConfigFileEnum;
import javacourse.task5.build.config.ConfigProperty;
import javacourse.task5.build.factory.Component;
import javacourse.task5.dao.AbstractDao;
import javacourse.task5.dao.entity.AbstractEntity;
import javacourse.task5.dao.entity.Guest;
import javacourse.task5.dao.entity.IdSupplier;
import javacourse.task5.dao.entity.Maintenance;
import javacourse.task5.dao.entity.Room;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public abstract class AbstractDaoImpl<T extends AbstractEntity> implements AbstractDao<T> {
    @ConfigProperty(configFileEnum = ConfigFileEnum.ROOM_JSON, type = Room[].class)
    @ConfigProperty(configFileEnum = ConfigFileEnum.GUEST_JSON, type = Guest[].class)
    @ConfigProperty(configFileEnum = ConfigFileEnum.MAINTENANCE_JSON, type = Maintenance[].class)
    private final List<T> repository = new ArrayList<>();
    private final IdSupplier idSupplier = new IdSupplier();

    @Override
    public abstract T getDaoEntity();

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
        repository.forEach(e -> {
            if (element.getId() == e.getId()) {
                throw new KeyAlreadyExistsException(element + " == " + e);
            }
        });
        repository.add(element);
    }

    @Override
    public void deleteFromRepo(T element) throws NoSuchElementException {
        if (!repository.remove(element)) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public long supplyId() {
        return idSupplier.supplyId();
    }

    @Override
    public void synchronizeNextSuppliedId(long id) {
        idSupplier.synchronizeNextSuppliedId(id);
    }
}
