package javacourse.task5.dao;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public interface AbstractDao<T> {
    List<T> getAll();
    T getById(long id) throws NoSuchElementException;
    T getByName(String fullName) throws NoSuchElementException;
    List<T> getSorted(List<T> listToSort, Comparator<T> comparator);

    void addToRepo(T element) throws KeyAlreadyExistsException;
    void deleteFromRepo(T element) throws NoSuchElementException;
    long supplyId();
    void synchronizeNextSuppliedId(long id);

    String exportData(T element);
    T getDaoEntity();
}
