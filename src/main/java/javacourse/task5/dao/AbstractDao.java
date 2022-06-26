package javacourse.task5.dao;

import javacourse.task5.build.orm.TransactionTaskFunction;
import org.hibernate.HibernateException;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public interface AbstractDao<T> {
    List<T> getAll();
    List<T> getQueryResult(CriteriaQuery<T> criteria, Class<T> clazz);
    T getById(long id) throws HibernateException;
    void addToRepo(T element) throws KeyAlreadyExistsException;
    void deleteFromRepo(T element) throws NoSuchElementException;

    String exportData(T element);
    T getDaoEntity();
    void openSessionAndExecuteTransactionTask(TransactionTaskFunction task);
}
