package task5.dao;

import task5.service.Hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Guest {
    private final String fullName;
    private final String passport;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private List<Maintenance> orderedMaintenances;
    private int payment;

    public Guest(String fullName, String passport, LocalDate checkInTime, LocalDate checkOutTime, Room room) {
        this.fullName = fullName;
        this.passport = passport;
        this.checkInDate = checkInTime;
        this.checkOutDate = checkOutTime;
        this.room = room;
    };

    public String getFullName() {
        return fullName;
    }

    public String getPassport() {
        return passport;
    }

    public void orderMaintenance(Hotel hotel, String maintenanceName) {
        if (orderedMaintenances == null) {
            orderedMaintenances = new ArrayList<>();
        }
        Maintenance currentMaintenance = hotel.getMaintenanceManager().getMaintenanceByName(maintenanceName).clone();
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
    }
}
