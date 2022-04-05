package task5.service;

import java.util.List;
import java.util.NoSuchElementException;

public interface AbstractService<T> {
    List<T> getAll();
    T getByName(String name) throws NoSuchElementException;
    T getById(int id) throws NoSuchElementException;
}
