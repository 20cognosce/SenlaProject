package javacourse.task5.dao.impl;

import javacourse.task5.build.factory.Component;
import javacourse.task5.build.orm.OrmManagementUtil;
import javacourse.task5.dao.AbstractDao;
import javacourse.task5.dao.entity.AbstractEntity;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public abstract class AbstractDaoImpl<T extends AbstractEntity> implements AbstractDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDaoImpl.class);

    @Override
    public abstract T getDaoEntity();

    @Override
    public List<T> getAll() {
        List<T> resultList;

        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            Class<T> clazz = (Class<T>) getDaoEntity().getClass();
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(clazz);
            Root<T> root = criteria.from(clazz);
            criteria.select(root);
            resultList = session.createQuery(criteria).getResultList();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return resultList;
    }

    @Override
    public T getById(long id) throws NoSuchElementException {
        T result;

        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            Class<T> clazz = (Class<T>) getDaoEntity().getClass();
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(clazz);
            Root<T> root = criteria.from(clazz);
            criteria.select(root).where(builder.equal(root.get("id"), id));
            result = session.createQuery(criteria).getSingleResult();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

        return result;
    }

    @Override
    public T getByName(String name) throws NoSuchElementException {
        T result;

        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            Class<T> clazz = (Class<T>) getDaoEntity().getClass();
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(clazz);
            Root<T> root = criteria.from(clazz);
            criteria.select(root).where(builder.equal(root.get("name"), name));
            result = session.createQuery(criteria).getSingleResult();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

        return result;
    }

    @Override
    public List<T> getSorted(List<T> listToSort, Comparator<T> comparator) {
        return listToSort.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public void addToRepo(T element) throws KeyAlreadyExistsException {
        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(element);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteFromRepo(T element) throws NoSuchElementException {
        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(element);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
