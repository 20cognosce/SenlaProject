package task5.dao;

import task5.controller.Main;
import task5.service.Hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Guest {
    private final String fullName;
    private final String passport;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private List<Maintenance> orderedMaintenances;
    private int payment = 0;

    public Guest(String fullName, String passport, LocalDate checkInTime, LocalDate checkOutTime, Room room) {
        this.fullName = fullName;
        this.passport = passport;
        this.checkInDate = checkInTime;
        this.checkOutDate = checkOutTime;
        this.room = room;
        if (!Objects.isNull(room)) {
            payment = room.getPrice();
        }
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassport() {
        return passport;
    }

    public void orderMaintenance(Maintenance maintenance) {
        if (orderedMaintenances == null) {
            orderedMaintenances = new ArrayList<>();
        }
        Maintenance currentMaintenance = maintenance.clone();
        currentMaintenance.execute(this);
        orderedMaintenances.add(currentMaintenance);
        setPayment(getPayment() + currentMaintenance.getPrice());
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
        return orderedMaintenances;
    }

    public String getOrderedMaintenancesAsString() {
        return "";
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
        if (!Objects.isNull(room) && room.getGuestsCurrentList().isEmpty()) {
            setPayment(getPayment() + room.getPrice());
        }
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        Guest guest = this;
            out.append("Гость: ").append(guest.getFullName())
                    .append("; Паспорт: ").append(guest.getPassport())
                    .append("; Номер: ");
            try {
                out.append(guest.getRoom().getRoomNumber());
            } catch (NullPointerException e) {
                out.append("без номера");
            }
            out.append("; Дата заезда: ").append(guest.getCheckInDate())
                    .append("; Дата освобождения: ").append(guest.getCheckOutDate())
                    .append("; Заказанные услуги: ").append(guest.getOrderedMaintenancesAsString())
                    .append("; К оплате: ")
                    .append(guest.getPayment())
                    .append("\n");
        return out.toString();
    }
}
