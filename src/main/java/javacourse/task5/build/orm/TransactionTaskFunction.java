package javacourse.task5.build.orm;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;

@FunctionalInterface
public interface TransactionTaskFunction {
    void execute (Session session, CriteriaBuilder criteriaBuilder);
}
