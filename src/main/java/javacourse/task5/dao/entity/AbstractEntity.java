package javacourse.task5.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter
    @Setter
    @Column(name="name")
    private String name; //Room number may include letters, so I called it name to inherit it
    @Getter
    @Setter
    @Column(name="price")
    private int price; //Guest's payment now is price for the same reason

    public AbstractEntity(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public AbstractEntity() {

    }
}
