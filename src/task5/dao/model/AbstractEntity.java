package task5.dao.model;

public class AbstractEntity {
    private final int ID;
    private String name; //Room number may include letters, so I called it name to inherit it
    private int price = 0; //Guest's payment now is price for the same reason

    public AbstractEntity(int id, String name, int price) {
        this.ID = id;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return ID;
    }
}
