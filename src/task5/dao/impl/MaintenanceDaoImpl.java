package task5.dao.impl;

import task5.dao.MaintenanceDao;
import task5.dao.model.Guest;
import task5.dao.model.IdSupplier;
import task5.dao.model.Maintenance;
import task5.dao.model.MaintenanceCategory;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
        repository.forEach(maintenance -> out.append(maintenance.toString()).append("\n"));
        return out.toString();
    }

    @Override
    public String getAllAsString(List<Maintenance> subList) {
        StringBuilder out = new StringBuilder();
        subList.forEach(maintenance -> out.append(maintenance.toString()).append("\n"));
        return out.toString();
    }

    @Override
    public String getMaintenancesOfGuest(Guest guest) {
        StringBuilder out = new StringBuilder("Гость: " + guest.getFullName() + "\nЗаказы:\n");
        guest.getOrderedMaintenances().forEach((maintenance) -> out.append(maintenance.toString()).append("; Дата: ")
                .append(maintenance.getOrderTime()
                        .truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME))
                .append("\n"));
        return out.toString();
    }

    @Override
    public String getMaintenancesOfGuest(Guest guest, Comparator<Maintenance> comparator) {
        StringBuilder out = new StringBuilder("Гость: " + guest.getFullName() + "\nЗаказы:\n");
        guest.getOrderedMaintenances().stream().sorted(comparator).forEach((maintenance) -> out
                .append(maintenance.toString()).append("; Дата: ")
                .append(maintenance.getOrderTime()
                        .truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME))
                .append("\n"));
        return out.toString();
    }

    @Override
    public List<Maintenance> getSorted(List<Maintenance> listToSort, Comparator<Maintenance> comparator) {
        List<Maintenance> sorted = new ArrayList<>();
        listToSort.stream().sorted(comparator)
                .forEach(sorted::add);
        return sorted;
    }
}
