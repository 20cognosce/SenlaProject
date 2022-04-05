package task5.dao.model;

public class AbstractEntity {
    private final long ID;
    private String name; //Room number may include letters, so I called it name to inherit it
    private int price; //Guest's payment now is price for the same reason

    public AbstractEntity(long id, String name, int price) {
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

    public long getId() {
        return ID;
    }
}
