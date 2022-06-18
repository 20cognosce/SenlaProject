package javacourse.task5.build.orm;

import javacourse.task5.dao.entity.Guest;
import javacourse.task5.dao.entity.Maintenance;
import javacourse.task5.dao.entity.Room;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class DatabaseOrmManager {
    public static <T> List<T> getListFromDatabase(Class<T> clazz) {
        SessionFactory sf = buildSessionFactory();
        Session session = sf.openSession();
        session.getTransaction().begin();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        criteria.from(clazz);
        List<T> resultList = session.createQuery(criteria).getResultList();

        session.close();
        return resultList;
    }

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();
            Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
