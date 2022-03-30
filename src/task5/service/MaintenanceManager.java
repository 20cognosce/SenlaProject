package task5.service;

import task5.dao.Guest;
import task5.dao.Maintenance;
import task5.dao.MaintenanceCategory;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class MaintenanceManager {
    MaintenanceManager(){}

    private final List<Maintenance> maintenances = new ArrayList<>();

    public void loadMaintenancesDatabase() {
        Maintenance maintenance1 = new Maintenance("Завтрак в номер", 500, MaintenanceCategory.LOCAL_FOOD);
        Maintenance maintenance2 = new Maintenance("Обед в номер", 600, MaintenanceCategory.LOCAL_FOOD);
        Maintenance maintenance3 = new Maintenance("Ужин в номер", 800, MaintenanceCategory.LOCAL_FOOD);
        Maintenance maintenance4 = new Maintenance("Принести доставку в номер", 100, MaintenanceCategory.DELIVERY_FOOD);
        Maintenance maintenance5 = new Maintenance("Дополнительный набор для душа", 200, MaintenanceCategory.ACCESSORIES);
        maintenances.add(maintenance1);
        maintenances.add(maintenance2);
        maintenances.add(maintenance3);
        maintenances.add(maintenance4);
        maintenances.add(maintenance5);
    }

    public void addNewMaintenance(Maintenance maintenance) {
        maintenances.add(maintenance);
    }

    public Maintenance getMaintenanceByName(String name) throws NoSuchElementException {
        Maintenance result = maintenances.stream()
                .filter(maintenance -> (name.equals(maintenance.getName())))
                .findFirst().orElse(null);
        if (result == null) {
            throw new NoSuchElementException();
        }
        return result;
    }

    public String getMaintenancesPriceList() {
        StringBuilder out = new StringBuilder();
        maintenances.forEach(maintenance -> out
                .append(maintenance.getName()).append("; Категория: ").append(maintenance.getCategory())
                .append("; Цена: ").append(maintenance.getPrice()).append("\n"));
        return out.toString();
    }

    public String getMaintenancesPriceList(Comparator<Maintenance> comparator) {
        StringBuilder out = new StringBuilder();
        maintenances.stream().sorted(comparator)
                .forEach(maintenance -> out.append(maintenance.toString()).append("\n"));
        return out.toString();
    }

    public String getMaintenancesOfGuest(Guest guest) {
        StringBuilder out = new StringBuilder("Гость: " + guest.getFullName() + "\nЗаказы:\n");
        guest.getOrderedMaintenances().forEach((maintenance) -> out.append(maintenance.toString()).append("; Дата: ")
                .append(maintenance.getOrderTime()
                        .truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME))
                .append("\n"));
        return out.toString();
    }

    public String getMaintenancesOfGuest(Guest guest, Comparator<Maintenance> comparator) {
        StringBuilder out = new StringBuilder("Гость: " + guest.getFullName() + "\nЗаказы:\n");
        guest.getOrderedMaintenances().stream().sorted(comparator).forEach((maintenance) -> out
                .append(maintenance.toString()).append("; Дата: ")
                .append(maintenance.getOrderTime()
                        .truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME))
                .append("\n"));
        return out.toString();
    }
}
