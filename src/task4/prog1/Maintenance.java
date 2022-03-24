package task4.prog1;

import java.time.LocalDateTime;

public class Maintenance implements Cloneable {
    private final String name;
    private int price;
    private final MaintenanceCategory category;
    private LocalDateTime orderTime;

    Maintenance(String serviceName, int price, MaintenanceCategory category) {
        this.name = serviceName;
        this.price = price;
        this.category = category;
    }

    String getName() {
        return this.name;
    }

    void execute(Guest guest) {
        LocalDateTime time = LocalDateTime.now();
        System.out.println("Услуга: " + getName() + " для " + guest.getFullName()
                + " исполнена. Цена услуги: " + getPrice() + "; Дата: " + time);
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
        return getName() + "; Категория: " + getCategory() + "; Цена: " + getPrice();
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
