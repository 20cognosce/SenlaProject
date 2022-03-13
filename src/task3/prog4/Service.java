package task3.prog4;

public class Service {
    private final String serviceName;
    private int price;

    Service(String serviceName, int price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    String getServiceName() {
        return this.serviceName;
    }

    void execute(Guest guest) {
        System.out.println("Услуга: " + getServiceName() + " для " + guest.getFullName()
                + " исполнена. Цена услуги: " + getPrice());
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
