package javacourse.task5.dao.entity;

import lombok.Getter;
import lombok.Setter;

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
public class Guest extends AbstractEntity {
    @Getter
    @Setter
    @Column(name="passport")
    private String passport;
    @Getter
    @Setter
    @Column(name="room_id")
    private Long roomId;
    @Getter
    @Setter
    @Column(name="check_in_date")
    private LocalDate checkInDate;
    @Getter
    @Setter
    @Column(name="check_out_date")
    private LocalDate checkOutDate;

    /*
    * На лекции советовали использовать LAZY, но чтобы его реализовать без LazyInitializationException
    * в интернете советуют использовать @Transactional
    * а Spring подключать не разрешено в задании
    * */
    @Getter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id")
    private final List<Maintenance> orderedMaintenances = new ArrayList<>();

    //new guest constructor
    public Guest(String name, String passport, LocalDate checkInTime, LocalDate checkOutTime, Long roomId) {
        super(name,  0);
        this.passport = passport;
        this.checkInDate = checkInTime;
        this.checkOutDate = checkOutTime;
        this.roomId = roomId;
    }

    //total constructor
    public Guest(String name, int price,
                 String passport, Long roomId,
                 LocalDate checkInDate, LocalDate checkOutDate, List<Maintenance> orderedMaintenances) {
        super(name, price);
        this.passport = passport;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.orderedMaintenances.addAll(orderedMaintenances);
    }

    public Guest() {
        super("", 0);
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
            } else if (getRoomId() == 0L) {
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
}
