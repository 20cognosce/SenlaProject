package task5.dao.model;

public class IdSupplier {
    private long id;

    public IdSupplier() {
        this.id = 0;
    }

    public long supplyId() {
        return ++id;
    }
}
