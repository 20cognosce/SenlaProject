package task5.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Guest extends AbstractEntity implements Cloneable {
    private String passport;
    @JsonBackReference
    private Room room;
    private long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private final List<Maintenance> orderedMaintenances = new ArrayList<>();


    //new guest constructor
    public Guest(long id, String name, String passport, LocalDate checkInTime, LocalDate checkOutTime, Room room) {
        super(id, name,  0);
        this.passport = passport;
        this.room = room;
        if (!Objects.isNull(room)) {
            setPrice(room.getPrice());
            roomId = room.getId();
        } else {
            roomId = 0;
        }
        this.checkInDate = checkInTime;
        this.checkOutDate = checkOutTime;
    }

    //total constructor
    public Guest(long id, String name, int price,
                 String passport, Room room, long roomId,
                 LocalDate checkInDate, LocalDate checkOutDate, List<Maintenance> orderedMaintenances) {
        super(id, name, price);
        this.passport = passport;
        this.room = room;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.orderedMaintenances.addAll(orderedMaintenances);
    }

    public Guest() {
        super(0, "", 0);
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
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

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public void setRoom(Room room) {
        this.room = room;
        if (Objects.isNull(room)) {
            roomId = 0;
        } else {
            roomId = room.getId();
        }
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

    @Override
    public Guest clone() throws CloneNotSupportedException {
        //TODO room is not cloned here
        Guest clone = (Guest) super.clone();
        clone.setPassport(this.getPassport());
        clone.setRoomId(this.getRoomId());
        clone.setCheckInDate(this.getCheckInDate());
        clone.setCheckOutDate(this.getCheckOutDate());
        this.orderedMaintenances.forEach(maintenance -> {
            try {
                clone.orderedMaintenances.add(maintenance.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
        return clone;
    }
}
