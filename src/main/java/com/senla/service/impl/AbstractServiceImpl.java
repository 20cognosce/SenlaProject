package com.senla.service.impl;

import com.senla.dao.AbstractDao;
import com.senla.model.AbstractEntity;
import com.senla.model.Guest2Maintenance;
import com.senla.service.AbstractService;
import com.senla.util.SortEnum;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
public abstract class AbstractServiceImpl<T extends AbstractEntity, D extends AbstractDao<T>> implements AbstractService<T, D> {

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
    public List<LocalDateTime> getGuest2MaintenanceOrderTime(long guestId, long maintenanceId) {
        List<Guest2Maintenance> orderEntries = getDefaultDao().getGuest2Maintenance(guestId, maintenanceId);

        List<LocalDateTime> resultList = new ArrayList<>();
        orderEntries.forEach(order -> resultList.add(order.getOrderTime()));
        return resultList;
    }

    @Override
    public String getFieldToSortFromEnum(SortEnum sort) {
        String fieldToSortBy;

        switch (sort) {
            case BY_ADDITION: fieldToSortBy = "id"; break;
            case BY_PRICE: fieldToSortBy = "price"; break;
            case BY_CATEGORY:  fieldToSortBy = "category"; break;
            case BY_TIME: fieldToSortBy = "orderTime"; break;
            case BY_ALPHABET:  fieldToSortBy = "name"; break;
            case BY_CHECKOUT_DATE: fieldToSortBy = "checkOutDate"; break;
            case BY_CAPACITY:  fieldToSortBy = "capacity"; break;
            case BY_STARS: fieldToSortBy = "starsNumber"; break;
            default: throw new NoSuchElementException("Such sortEnum does not exist");
        }

        return fieldToSortBy;
    }

    @Override
    public <O, DTO> O updateEntityFromDto(O original, Class<O> originalClass, DTO dto) {
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
                log.error(e.getLocalizedMessage(), e);
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                log.error(e.getLocalizedMessage(), e);
            }
        });
        return original;
    }
}
