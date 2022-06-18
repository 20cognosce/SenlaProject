package javacourse.task5.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@Table(name="maintenance_instance")
public class Maintenance extends AbstractEntity implements Cloneable {
    @Column(name="category")
    @Enumerated(EnumType.STRING)
    private MaintenanceCategory category;
    @Column(name="order_timestamp")
    private LocalDateTime orderTime;

    @Column(name="guest_id")
    private Long guestId;

    public Maintenance(long id, String name, int price, MaintenanceCategory category, LocalDateTime orderTime) {
        super(id, name,  price);
        this.category = category;
        this.orderTime = orderTime;
        this.guestId = 0L;
    }

    public Maintenance() {
        super(0, "", 0);
        category = null;
        orderTime = null;
        guestId = 0L;
    }

    public MaintenanceCategory getCategory() {
        return category;
    }

    public void setCategory(MaintenanceCategory category) {
        this.category = category;
    }

    public LocalDateTime getOrderTime() throws NullPointerException {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public Long getGuestId() {
        return guestId;
    }

    public Long setGuestId(Long guestId) {
        return this.guestId = guestId;
    }

    @Override
    public String toString() {
        String orderTime;

        if (Objects.isNull(getOrderTime())) orderTime = "Не заказывалось";
        else orderTime = getOrderTime().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME);

        return "id: " + getId() +
                "; Услуга: " + getName() +
                "; Цена: " + getPrice() +
                "; Категория: " + getCategory() +
                "; Время заказа: " + orderTime +
                "; id гостя: " + getGuestId() +
                "\n";
    }

    @Override
    public Maintenance clone() throws CloneNotSupportedException {
        Maintenance clone = (Maintenance) super.clone();
        clone.setOrderTime(LocalDateTime.now());
        return clone;
    }
}
