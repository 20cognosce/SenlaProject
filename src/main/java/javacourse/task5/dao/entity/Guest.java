package javacourse.task5.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Guest extends AbstractEntity implements Cloneable {
    @Column(name="passport")
    private String passport;
    @Column(name="room_id")
    private Long roomId;
    @Column(name="check_in_date")
    private LocalDate checkInDate;
    @Column(name="check_out_date")
    private LocalDate checkOutDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id")
    private final List<Maintenance> orderedMaintenances = new ArrayList<>();

    //new guest constructor
    public Guest(long id, String name, String passport, LocalDate checkInTime, LocalDate checkOutTime, long roomId) {
        super(id, name,  0);
        this.passport = passport;
        this.checkInDate = checkInTime;
        this.checkOutDate = checkOutTime;
        this.roomId = roomId;
    }

    //total constructor
    public Guest(long id, String name, int price,
                 String passport, long roomId,
                 LocalDate checkInDate, LocalDate checkOutDate, List<Maintenance> orderedMaintenances) {
        super(id, name, price);
        this.passport = passport;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
            out.append("id: ").append(getId())
                    .append("; Гость: ").append(getName())
                    .append("; Паспорт: ").append(getPassport())
                    .append("; Комната: ");
            if (Objects.isNull(getRoomId())) {
                out.append("без комнаты");
            } else {
                out.append(getRoomId());
            }
            out.append("; Дата заезда: ").append(getCheckInDate())
                    .append("; Дата выезда: ").append(getCheckOutDate())
                    .append("; К оплате: ").append(getPrice())
                    .append("\n");
        return out.toString();
    }

    @Override
    public Guest clone() throws CloneNotSupportedException {
        /*
        clone.setPassport(this.getPassport());
        clone.setRoomId(this.getRoomId());
        clone.setCheckInDate(this.getCheckInDate());
        clone.setCheckOutDate(this.getCheckOutDate());
        clone.orderedMaintenances.clear();
        for (Maintenance maintenance : this.getOrderedMaintenances()) {
            clone.orderedMaintenances.add(maintenance.clone());
        }*/
        return (Guest) super.clone();
    }
}
