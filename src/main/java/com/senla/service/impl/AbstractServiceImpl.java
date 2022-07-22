package com.senla.service.impl;

import com.senla.dao.AbstractDao;
import com.senla.model.AbstractEntity;
import com.senla.service.AbstractService;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public abstract class AbstractServiceImpl<T extends AbstractEntity, D extends AbstractDao<T>> implements AbstractService<T, D> {

    protected abstract D getDefaultDao();

    @Override
    public T getById(long id) throws NoSuchElementException {
        return getDefaultDao().getById(id);
    }

    @Override
    @Transactional
    public void addAll(List<T> list) {
        list.forEach(e -> getDefaultDao().create(e));
    }

    @Override
    public List<T> getAll() {
        return getDefaultDao().getAll("id");
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
                * TODO:
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
 }
