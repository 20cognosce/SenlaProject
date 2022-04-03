package task5.dao.model;

public class IdSupplier {
    private int id;

    public IdSupplier() {
        this.id = -1;
    }

    public int supplyId() {
        return ++id;
    }
}
