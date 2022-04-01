package task4.prog1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Guest {
    private final String fullName;
    private final String passport;
    private Room room;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private List<Maintenance> orderedMaintenances;
    private int payment;

    public Guest(String fullName, String passport, LocalDateTime checkInTime, LocalDateTime checkOutTime, Room room) {
        this.fullName = fullName;
        this.passport = passport;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
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
        Maintenance currentMaintenance = hotel.maintenanceManager.getMaintenanceByName(maintenanceName).clone();
        currentMaintenance.execute(this);
        orderedMaintenances.add(currentMaintenance);
        setPayment(getPayment() + currentMaintenance.getPrice());
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
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
