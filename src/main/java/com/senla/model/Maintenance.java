package com.senla.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
public class Maintenance extends AbstractEntity {
    @Getter
    @Setter
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private MaintenanceCategory category;
    @Getter
    @Setter
    @Column(name = "order_timestamp")
    private LocalDateTime orderTime;
    @Getter
    @Setter
    @JoinColumn(name = "guest_id", referencedColumnName = "id")
    @ManyToOne //Optional = false не принимает null из таблицы в методе entityManager.find
    private Guest guest;
    @Getter
    @Setter
    @Column(name = "name")
    private String name;
    @Getter
    @Setter
    @Column(name = "price")
    private int price;

    public Maintenance(String name, int price, MaintenanceCategory category, LocalDateTime orderTime, Guest guest) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.orderTime = orderTime;
        this.guest = guest;
    }

    public Maintenance() {

    }

    @Override
    public String toString() {
        String orderTime;
        String guestId;

        if (Objects.isNull(getOrderTime())) {
            orderTime = "";
        } else {
            orderTime = "; Время заказа: " + getOrderTime().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME);
        }

        if (Objects.isNull(getGuest())) {
            guestId = "";
        } else {
            guestId = "; id гостя: " + getGuest().getId();
        }

        return "id: " + getId() +
                "; Услуга: " + getName() +
                "; Цена: " + getPrice() +
                "; Категория: " + getCategory() +
                orderTime +
                guestId +
                "\n";
    }

    @JsonIgnore
    public Maintenance getCloneInstance() {
        Maintenance clone = new Maintenance();
        clone.setCategory(this.getCategory());
        clone.setGuest(this.getGuest());
        clone.setName(this.getName());
        clone.setPrice(this.getPrice());
        clone.setOrderTime(LocalDateTime.now());
        return clone;
    }
}
