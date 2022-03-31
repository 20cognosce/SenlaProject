package task5.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Maintenance implements Cloneable {
    private final String name;
    private int price;
    private final MaintenanceCategory category;
    private LocalDateTime orderTime;

    public Maintenance(String maintenanceName, int price, MaintenanceCategory category) {
        this.name = maintenanceName;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public void execute(Guest guest) {
        LocalDateTime time = LocalDateTime.now();
        System.out.println("Услуга " + getName()
                + " для " + guest.getFullName()
                + " исполнена. Цена услуги: " + getPrice()
                + "; Дата: " + time.truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME));
        orderTime = time;
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
        return "Услуга: " + getName() + "; Цена: " + getPrice() + "; Категория: " + getCategory();
    }

    @Override
    public Maintenance clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (Maintenance) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
