package javacourse.task5.dao.entity;

import lombok.Getter;
import lombok.Setter;

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
@Table(name="maintenance")
public class Maintenance extends AbstractEntity implements Cloneable {
    @Getter
    @Setter
    @Column(name="category")
    @Enumerated(EnumType.STRING)
    private MaintenanceCategory category;
    @Getter
    @Setter
    @Column(name="order_timestamp")
    private LocalDateTime orderTime;
    @Getter
    @Setter
    @Column(name="guest_id")
    private Long guestId;

    public Maintenance(String name, int price, MaintenanceCategory category, LocalDateTime orderTime, Long guestId) {
        super(name,  price);
        this.category = category;
        this.orderTime = orderTime;
        this.guestId = guestId;
    }

    public Maintenance() {
        super("", 0);
        category = null;
        orderTime = null;
        guestId = 0L;
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

        if (Objects.isNull(getGuestId())) {
            guestId = "";
        } else {
            guestId = "; id гостя: " + getGuestId();
        }

        return "id: " + getId() +
                "; Услуга: " + getName() +
                "; Цена: " + getPrice() +
                "; Категория: " + getCategory() +
                orderTime +
                guestId +
                "\n";
    }

    @Override
    public Maintenance clone() throws CloneNotSupportedException {
        Maintenance clone = (Maintenance) super.clone();
        clone.setOrderTime(LocalDateTime.now());
        return clone;
    }
}
