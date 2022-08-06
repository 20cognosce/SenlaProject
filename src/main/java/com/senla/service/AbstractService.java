package com.senla.service;

import com.senla.dao.AbstractDao;
import com.senla.model.AbstractEntity;
import com.senla.util.SortEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface AbstractService<T extends AbstractEntity, D extends AbstractDao<T>> {

    void addAll(List<T> list);
    List<T> getAll(String fieldToSortBy, String order);
    T getById(long id);

    List<LocalDateTime> getGuest2MaintenanceOrderTime(long guestId, long maintenanceId);

    String getFieldToSortFromEnum(SortEnum sort);

    <O, DTO> O updateEntityFromDto(O original, DTO dto, Class<O> originalClass);
}
