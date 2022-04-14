package task5.dao.entity;

public class IdSupplier {
    private long id;

    public IdSupplier() {
        this.id = 0;
    }

    public long supplyId() {
        return ++id;
    }

    public void synchronizeNextSuppliedId(long id) {
        while (this.id + 1 != id) {
            supplyId();
        }
    }
}
