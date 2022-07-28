package com.senla.service.impl;

import com.senla.build.config.SortEnum;
import com.senla.dao.AbstractDao;
import com.senla.model.AbstractEntity;
import com.senla.service.AbstractService;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public abstract class AbstractServiceImpl<T extends AbstractEntity, D extends AbstractDao<T>> implements AbstractService<T, D> {

    @PersistenceContext
    EntityManager entityManager;
    protected abstract D getDefaultDao();

    @Override
    public T getById(long id) throws NoSuchElementException {
        return getDefaultDao().getById(id);
    }

    @Override
    public void addAll(List<T> list) {
        list.forEach(e -> getDefaultDao().create(e));
    }

    @Override
    public List<T> getAll(String fieldToSortBy, String order) {
        return getDefaultDao().getAll(fieldToSortBy, order);
    }

    @Override
    public <O, DTO> O updateEntityFromDto(O original, DTO dto) {
        Field[] dtoFields = dto.getClass().getDeclaredFields();

        Arrays.stream(dtoFields).forEach(dtoField -> {
            try {
                dtoField.setAccessible(true);
                if (Objects.isNull(dtoField.get(dto))) {
                    return;
                }
                Field originalField = original.getClass().getDeclaredField(dtoField.getName());
                originalField.setAccessible(true);
                originalField.set(original, dtoField.get(dto));

                /*Что делать с null полем в запросе? Если поле не указывать, то оно все равно передается в виде null
                * Не сеттить null вообще? Но кажется, что когда-то через запрос присвоить null будет полезно
                * Но тогда в запросе нужно передавать текущие значения сущности, чтобы они обновились на самих себя
                *
                * Решил не сеттить null вообще, временная заглушка пока не пойму как это надо делать
                */
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        });
        return original;
    }

    @Transactional
    @Override
    public List<LocalDateTime> getGuest2MaintenanceOrderTime(long guestId, long maintenanceId) {
        return getDefaultDao().getGuest2MaintenanceOrderTime(guestId, maintenanceId);
    }

    @Override
    public String getFieldToSortFromEnum(SortEnum sort) {
        String fieldToSort;

        switch (sort) {
            case BY_ADDITION: fieldToSort = "id"; break;
            case BY_PRICE: fieldToSort = "price"; break;
            case BY_CATEGORY:  fieldToSort = "category"; break;
            case BY_TIME: fieldToSort = "orderTime"; break;
            case BY_ALPHABET:  fieldToSort = "name"; break;
            case BY_CHECKOUT_DATE:fieldToSort = "checkOutDate"; break;
            case BY_CAPACITY:  fieldToSort = "capacity"; break;
            case BY_STARS: fieldToSort = "starsNumber"; break;
            default: throw new NoSuchElementException("Such sortEnum does not exist");
        }

        return fieldToSort;
    }
}
