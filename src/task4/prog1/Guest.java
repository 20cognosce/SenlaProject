package task4.prog1;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Guest {
    private final String fullName;
    private final String passport;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private ArrayList<Service> orderedServices;
    private int payment;

    public Guest(String fullName, String passport, LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        this.fullName = fullName;
        this.passport = passport;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
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
        Service currentService = hotel.serviceManager.getServiceByName(serviceName).clone();
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

    public ArrayList<Service> getOrderedServices() {
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
}
