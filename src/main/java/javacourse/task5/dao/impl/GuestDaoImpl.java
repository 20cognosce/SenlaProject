package javacourse.task5.dao.impl;

import javacourse.task5.build.factory.Component;
import javacourse.task5.build.orm.OrmManagementUtil;
import javacourse.task5.dao.GuestDao;
import javacourse.task5.dao.entity.Guest;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class GuestDaoImpl extends AbstractDaoImpl<Guest> implements GuestDao {
    private static final Logger logger = LoggerFactory.getLogger(GuestDaoImpl.class);

    public GuestDaoImpl() {
        super();
    }

    @Override
    public void updatePrice(long guestId, int price) {
        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            session.beginTransaction();
            Guest guest = getById(guestId);
            guest.setPrice(price);
            session.update(guest);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void updateRoomId(long guestId, long roomId) {
        try (Session session = OrmManagementUtil.sessionFactory.openSession()) {
            session.beginTransaction();
            Guest guest = getById(guestId);
            if (roomId == 0) {
                guest.setRoomId(null);
            } else {
                guest.setRoomId(roomId);
            }
            session.update(guest);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public String exportData(Guest guest) {
        return guest.getId() + "," +
                guest.getName() + "," +
                guest.getPassport() + "," +
                guest.getCheckInDate() + "," +
                guest.getCheckOutDate() + "," +
                guest.getRoomId();
    }

    @Override
    public Guest getDaoEntity() {
        return new Guest();
    }
}
