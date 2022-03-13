package task3.prog4;

public class Guest {
    private final String fullName;
    private final String passport;

    public Guest(String fullName, String passport) {
        this.fullName = fullName;
        this.passport = passport;
    };

    public String getFullName() {
        return fullName;
    }

    public String getPassport() {
        return passport;
    }

    public void orderService(Hotel hotel, String serviceName) {
        hotel.getServiceByName(serviceName).execute(this);
    }
}
