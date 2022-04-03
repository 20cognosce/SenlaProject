package task5.dao.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Guest {
    private final int ID;
    private final String fullName;
    private final String passport;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private final List<Maintenance> orderedMaintenances;
    private int payment = 0;

    public Guest(int id,
                 String fullName, String passport,
                 LocalDate checkInTime, LocalDate checkOutTime,
                 Room room) {
        this.ID = id;
        this.fullName = fullName;
        this.passport = passport;
        this.checkInDate = checkInTime;
        this.checkOutDate = checkOutTime;
        this.room = room;
        if (!Objects.isNull(room)) {
            payment = room.getPrice();
        }
        this.orderedMaintenances = new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassport() {
        return passport;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public List<Maintenance> getOrderedMaintenances() {
        return new ArrayList<>(orderedMaintenances);
    }

    public void addMaintenance(Maintenance maintenance) {
        orderedMaintenances.add(maintenance);
    }

    public String getOrderedMaintenancesAsString() {
        StringBuilder out = new StringBuilder();
        getOrderedMaintenances().forEach(maintenance -> out.append(maintenance.toString()));
        return out.toString();
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public Room getRoom() throws NullPointerException {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
        updatePayment();
    }

    public void updatePayment() {
        Room room = getRoom();
        if (!Objects.isNull(room) && room.getGuestsCurrentList().isEmpty()) {
            setPayment(getPayment() + room.getPrice());
        }
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
            out.append("id: ").append(getId())
                    .append("Гость: ").append(getFullName())
                    .append("; Паспорт: ").append(getPassport())
                    .append("; Комната: ");
            try {
                out.append(getRoom().getRoomNumber());
            } catch (NullPointerException e) {
                out.append("без комнаты");
            }
            out.append("; Дата заезда: ").append(getCheckInDate())
                    .append("; Дата освобождения: ").append(getCheckOutDate())
                    .append("; Заказанные услуги: ").append(getOrderedMaintenancesAsString())
                    .append("; К оплате: ").append(getPayment())
                    .append("\n");
        return out.toString();
    }

    public int getId() {
        return ID;
    }
}
