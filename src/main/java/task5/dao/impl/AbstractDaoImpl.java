package task5.dao.impl;

import task5.build.config.ConfigFileEnum;
import task5.build.config.ConfigProperty;
import task5.dao.AbstractDao;
import task5.dao.entity.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public abstract class AbstractDaoImpl<T extends AbstractEntity> implements AbstractDao<T> {
    @ConfigProperty(configFileEnum = ConfigFileEnum.ROOM_JSON, type = Room[].class)
    @ConfigProperty(configFileEnum = ConfigFileEnum.GUEST_JSON, type = Guest[].class)
    @ConfigProperty(configFileEnum = ConfigFileEnum.MAINTENANCE_JSON, type = Maintenance[].class)
    private final List<T> repository = new ArrayList<>();
    private final IdSupplier idSupplier = new IdSupplier();
    private final Class<T[]> typeParameterClassArray;

    AbstractDaoImpl(Class<T[]> typeParameterClassArray) {
        this.typeParameterClassArray = typeParameterClassArray;
    }

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
    public void synchronizeNextSuppliedId(long id) {
        idSupplier.synchronizeNextSuppliedId(id);
    }
}
