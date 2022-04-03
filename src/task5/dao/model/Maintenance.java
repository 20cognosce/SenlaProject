package task5.dao.model;

import java.time.LocalDateTime;

public class Maintenance {
    private final int ID;
    private final String name;
    private int price;
    private final MaintenanceCategory category;
    private LocalDateTime orderTime;

    public Maintenance(int ID, String maintenanceName, int price, MaintenanceCategory category) {
        this.ID = ID;
        this.name = maintenanceName;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public MaintenanceCategory getCategory() {
        return category;
    }

    public LocalDateTime getOrderTime() throws NullPointerException {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public String toString() {
        return "id: " + getId() +
                "; Услуга: " + getName() +
                "; Цена: " + getPrice() +
                "; Категория: " + getCategory() +
                "\n";
    }

    public int getId() {
        return ID;
    }
}
