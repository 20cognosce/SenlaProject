package task5.dao.impl;

import task5.config.ConfigFileEnum;
import task5.config.ConfigProperty;
import task5.dao.GuestDao;
import task5.dao.entity.Guest;
import task5.dao.entity.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class GuestDaoImpl extends AbstractDaoImpl<Guest> implements GuestDao {
    @ConfigProperty(configFileEnum = ConfigFileEnum.ARCHIVED_GUEST_JSON, type = Guest[].class)
    private final List<Guest> archivedRepository = new ArrayList<>();

    public GuestDaoImpl() {
        super(Guest[].class);
    }

    @Override
    public void updatePrice(Guest guest, Room room) {
        //pay only the first settled after the room was empty
        if (!Objects.isNull(room) && room.getCurrentGuestIdList().size() == 1) {
            guest.setPrice(room.getPrice());
        }
    }

    @Override
    public void addToArchivedRepository(Guest guest) throws CloneNotSupportedException {
        archivedRepository.add(guest.clone());
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
    public Guest getFromArchivedRepositoryById(long id) throws NoSuchElementException {
        return archivedRepository.stream()
                .filter(element -> (element.getId() == id))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Guest> getArchivedAll() {
        return new ArrayList<>(archivedRepository);
    }
}
