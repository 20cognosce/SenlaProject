package com.senla.javacourse.dao.impl;

import com.senla.javacourse.build.orm.OrmManagementUtil;
import com.senla.javacourse.build.orm.TransactionTaskFunction;
import com.senla.javacourse.dao.AbstractDao;
import com.senla.javacourse.dao.entity.AbstractEntity;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class AbstractDaoImpl<T extends AbstractEntity> implements AbstractDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDaoImpl.class);

    @Override
    public abstract T getDaoEntity();

    public List<T> getQueryResult(CriteriaQuery<T> criteria, Class<T> clazz) {
        var ref = new Object() {
            List<T> resultQuery;
        };
        openSessionAndExecuteTransactionTask((session, criteriaBuilder) -> {
            Root<T> root = criteria.from(clazz);
            criteria.select(root);
            ref.resultQuery = session.createQuery(criteria).getResultList();
        });
        return ref.resultQuery;
    }

    @Override
    public List<T> getAll() {
        List<T> resultList;

        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            Class<T> clazz = (Class<T>) getDaoEntity().getClass();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(clazz);
            Root<T> root = criteria.from(clazz);
            criteria.select(root);
            resultList = session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return resultList;
    }

    @Override
    public T getById(long id) {
        var ref = new Object() {
            T result;
        };
        openSessionAndExecuteTransactionTask((session, builder) -> {
            Class<T> clazz = (Class<T>) getDaoEntity().getClass();
            CriteriaQuery<T> criteria = builder.createQuery(clazz);
            Root<T> root = criteria.from(clazz);
            criteria.select(root).where(builder.equal(root.get("id"), id));
            ref.result = session.createQuery(criteria).getSingleResult();
        });
        return ref.result;
    }

    @Override
    public void addToRepo(T element) throws KeyAlreadyExistsException {
        openSessionAndExecuteTransactionTask((session, builder) -> session.save(element));
    }

    @Override
    public void deleteFromRepo(T element) throws NoSuchElementException {
        openSessionAndExecuteTransactionTask((session, builder) -> session.delete(element));
    }

    public void openSessionAndExecuteTransactionTask(TransactionTaskFunction task) {
        Session session = OrmManagementUtil.sessionFactory.openSession();
        try {
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            task.execute(session, criteriaBuilder);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            session.getTransaction().rollback();
            throw e;
        }
    }
}
