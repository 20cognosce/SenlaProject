package com.senla.javacourse.dao.impl;

import com.senla.javacourse.dao.AbstractDao;
import com.senla.javacourse.dao.entity.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractDaoImpl<T extends AbstractEntity> implements AbstractDao<T> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(T element) {
        entityManager.persist(element);
    }

    @Override
    public void delete(T element) {
        entityManager.remove(element);
    }

    @Override
    public void update(T element) {
        entityManager.merge(element);
    }

    /*public List<T> getQueryResult(CriteriaQuery<T> criteria, Class<T> clazz) {
        var ref = new Object() {
            List<T> resultQuery;
        };
        openSessionAndExecuteTransactionTask((session, criteriaBuilder) -> {
            Root<T> root = criteria.from(clazz);
            criteria.select(root);
            ref.resultQuery = session.createQuery(criteria).getResultList();
        });
        return ref.resultQuery;
    }*/

    /*public void openSessionAndExecuteTransactionTask(TransactionTaskFunction task) {
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
    }*/
}
