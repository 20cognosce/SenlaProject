package task3.prog4;

public class Service {
    private final String name;
    private int price;

    Service(String name, int price) {
        this.name = name;
        this.price = price;
    }

    String getName() {
        return this.name;
    }

    void execute(Guest guest) {
        System.out.println("Услуга: " + getName() + " для " + guest.getFullName()
                + " исполнена. Цена услуги: " + getPrice());
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
