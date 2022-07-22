package com.senla.service;

import com.senla.dao.AbstractDao;
import com.senla.model.AbstractEntity;

import java.util.List;

public interface AbstractService<T extends AbstractEntity, D extends AbstractDao<T>> {

    void addAll(List<T> list);
    List<T> getAll();
    T getById(long id);

    <O, DTO> O updateEntityFromDto(O original, DTO dto);
}
