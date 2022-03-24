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
    private List<Maintenance> orderedServices;
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

    public void orderService(Hotel hotel, String serviceName) {
        if (orderedServices == null) {
            orderedServices = new ArrayList<>();
        }
        Maintenance currentService = hotel.serviceManager.getServiceByName(serviceName).clone();
        currentService.execute(this);
        orderedServices.add(currentService);
        setPayment(getPayment() + currentService.getPrice());
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

    public List<Maintenance> getOrderedServices() {
        return orderedServices;
    }

    public String getOrderedServicesAsString() {
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
