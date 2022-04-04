package task5.dao.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Maintenance extends AbstractEntity {
    private MaintenanceCategory category;
    private LocalDateTime orderTime;

    public Maintenance(int id, String name, int price, MaintenanceCategory category) {
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
        try {
            orderTime = getOrderTime().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e) {
            orderTime = "Не заказывалось";
        }

        return "id: " + getId() +
                "; Услуга: " + getName() +
                "; Цена: " + getPrice() +
                "; Категория: " + getCategory() +
                "; Время заказа: " + orderTime +
                "\n";
    }
}
