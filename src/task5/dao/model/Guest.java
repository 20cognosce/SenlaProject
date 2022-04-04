package task5.dao.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Guest extends AbstractEntity {
    private final String passport;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private final List<Maintenance> orderedMaintenances = new ArrayList<>();


    public Guest(int id, String name, String passport, LocalDate checkInTime, LocalDate checkOutTime, Room room) {
        super(id, name,  0);
        if (!Objects.isNull(room)) {
            setPrice(room.getPrice());
        }
        this.passport = passport;
        this.checkInDate = checkInTime;
        this.checkOutDate = checkOutTime;
        this.room = room;
    }

    public void updatePrice() {
        Room room = getRoom();
        //pay only the first settled after the room was empty
        if (!Objects.isNull(room) && room.getGuestsCurrentList().size() == 1) {
            setPrice(getPrice() + room.getPrice());
        }
    }

    public String getPassport() {
        return passport;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public List<Maintenance> getOrderedMaintenances() {
        return new ArrayList<>(orderedMaintenances);
    }

    public void addMaintenance(Maintenance maintenance) {
        orderedMaintenances.add(maintenance);
    }

    public Room getRoom() throws NullPointerException {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
        updatePrice();
    }

    public String getOrderedMaintenancesAsString() {
        return "Заказанные услуги: " + getOrderedMaintenances();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
            out.append("id: ").append(getId())
                    .append("; Гость: ").append(getName())
                    .append("; Паспорт: ").append(getPassport())
                    .append("; Комната: ");
            try {
                out.append(getRoom().getName());
            } catch (NullPointerException e) {
                out.append("без комнаты");
            }
            out.append("; Дата заезда: ").append(getCheckInDate())
                    .append("; Дата выезда: ").append(getCheckOutDate())
                    .append("; К оплате: ").append(getPrice())
                    .append("\n");
        return out.toString();
    }
}
