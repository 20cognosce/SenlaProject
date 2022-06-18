package javacourse.task5.dao.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(name="name")
    private String name; //Room number may include letters, so I called it name to inherit it
    @Column(name="price")
    private int price; //Guest's payment now is price for the same reason

    public AbstractEntity(long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public AbstractEntity() {

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
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
