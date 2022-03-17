package task4.prog1;

import java.time.LocalDateTime;

public class Service implements Cloneable {
    private final String serviceName;
    private int price;
    private final ServiceCategory category;
    private LocalDateTime orderTime;

    Service(String serviceName, int price, ServiceCategory category) {
        this.serviceName = serviceName;
        this.price = price;
        this.category = category;
    }

    String getServiceName() {
        return this.serviceName;
    }

    void execute(Guest guest) {
        LocalDateTime time = LocalDateTime.now();
        System.out.println("Услуга: " + getServiceName() + " для " + guest.getFullName()
                + " исполнена. Цена услуги: " + getPrice() + "; Дата: " + time);
        orderTime = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ServiceCategory getCategory() {
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
        return getServiceName() + "; Категория: " + getCategory() + "; Цена: " + getPrice();
    }

    @Override
    public Service clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (Service) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
