package task5.dao.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Maintenance extends AbstractEntity implements Cloneable {
    private MaintenanceCategory category;
    private LocalDateTime orderTime;

    public Maintenance(long id, String name, int price, MaintenanceCategory category) {
        super(id, name,  price);
        this.category = category;
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
                "\n";
    }

    @Override
    public Maintenance clone() throws CloneNotSupportedException {
        Maintenance clone = (Maintenance) super.clone();
        clone.setOrderTime(LocalDateTime.now());
        return clone;
    }
}
