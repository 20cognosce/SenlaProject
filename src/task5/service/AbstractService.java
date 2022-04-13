package task5.service;

import java.util.List;
import java.util.NoSuchElementException;

public interface AbstractService<T> {
    List<T> getAll();
    T getByName(String name) throws NoSuchElementException;
    T getById(long id) throws NoSuchElementException;

    String exportData(long id) throws NoSuchElementException, ClassNotFoundException;
    void importData(List<List <String>> records);
}
