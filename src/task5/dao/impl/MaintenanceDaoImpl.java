package task5.dao.impl;

import task5.dao.MaintenanceDao;
import task5.dao.model.Guest;
import task5.dao.model.IdSupplier;
import task5.dao.model.Maintenance;
import task5.dao.model.MaintenanceCategory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class MaintenanceDaoImpl implements MaintenanceDao {
    private final IdSupplier idSupplier = new IdSupplier();
    private final List<Maintenance> repository = new ArrayList<>();

    @Override
    public void createMaintenance(String maintenanceName, int price, MaintenanceCategory category) {
        repository.add(new Maintenance(idSupplier.supplyId(), maintenanceName, price, category));
    }

    @Override
    public Maintenance getMaintenanceByName(String name) throws NoSuchElementException {
        return repository.stream()
                .filter(maintenance -> (name.equals(maintenance.getName())))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Maintenance getMaintenanceById(int id) throws NoSuchElementException {
        return repository.stream()
                .filter(maintenance -> (maintenance.getId() == id))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Maintenance> getAll() {
        return new ArrayList<>(repository);
    }

    @Override
    public String getAllAsString() {
        StringBuilder out = new StringBuilder();
        repository.forEach(maintenance -> out.append(maintenance.toString()));
        return out.toString();
    }

    @Override
    public String getAsString(List<Maintenance> subList) {
        StringBuilder out = new StringBuilder();
        subList.forEach(maintenance -> out.append(maintenance.toString()));
        return out.toString();
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuest(Guest guest) {
        return guest.getOrderedMaintenances();
    }

    @Override
    public List<Maintenance> getMaintenancesOfGuest(Guest guest, Comparator<Maintenance> comparator) {
        List<Maintenance> sorted = new ArrayList<>();
        guest.getOrderedMaintenances().stream().sorted(comparator)
                .forEach(sorted::add);
        return sorted;
    }

    @Override
    public List<Maintenance> getSorted(List<Maintenance> listToSort, Comparator<Maintenance> comparator) {
        List<Maintenance> sorted = new ArrayList<>();
        listToSort.stream().sorted(comparator)
                .forEach(sorted::add);
        return sorted;
    }
}
