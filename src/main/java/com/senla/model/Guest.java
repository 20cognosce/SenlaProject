package com.senla.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Guest extends AbstractEntity {
    @Getter
    @Setter
    @Column(name = "passport")
    private String passport;
    @Getter
    @Setter
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @Getter
    @Setter
    @Column(name = "check_in_date")
    private LocalDate checkInDate;
    @Getter
    @Setter
    @Column(name = "check_out_date")
    private LocalDate checkOutDate;
    @Getter
    @Setter
    @Column(name = "name")
    private String name;
    @Getter
    @Setter
    @Column(name = "price")
    private int price;
    @Setter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "guest")
    private List<Maintenance> orderedMaintenances = new ArrayList<>();

    //total constructor
    public Guest(String name, int price, String passport, LocalDate checkInDate, LocalDate checkOutDate,
                 Room room, List<Maintenance> orderedMaintenances) {
        this.name = name;
        this.price = price;
        this.passport = passport;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.orderedMaintenances = orderedMaintenances;
    }

    public Guest() {

    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
            out.append("id: ").append(getId())
                    .append("; Гость: ").append(getName())
                    .append("; Паспорт: ").append(getPassport())
                    .append("; Комната: ");
            if (Objects.isNull(getRoom())) {
                out.append("без комнаты");
            } else {
                out.append(getRoom().getId());
            }
            out.append("; Дата заезда: ").append(getCheckInDate())
                    .append("; Дата выезда: ").append(getCheckOutDate())
                    .append("; К оплате: ").append(getPrice())
                    .append("\n");
        return out.toString();
    }
}
