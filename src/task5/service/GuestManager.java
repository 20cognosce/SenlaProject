package task5.service;

import task5.dao.Guest;
import task5.dao.Room;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class GuestManager {
    GuestManager(){}

    private final List<Guest> guests = new ArrayList<>();

    public Guest createGuest(String fullName, String passport,
                             LocalDate checkInTime, LocalDate checkOutTime,
                             Room room) {
        return new Guest(fullName, passport, checkInTime, checkOutTime, room);
    }

    public void loadGuestsDatabase() {
        Guest guest1 = new Guest("Ivanov Ivan Ivanovich", "1111 222222",
                LocalDate.of(2022, 3, 2),
                LocalDate.of(2022, 3, 5), null);
        Guest guest2 = new Guest("Ivanova Maria Ivanovna", "3333 444444",
                LocalDate.of(2022, 3, 2),
                LocalDate.of(2022, 3, 5), null);
        Guest guest3 = new Guest("Petrov Petr Petrovich", "5555 666666",
                LocalDate.of(2022, 3, 1),
                LocalDate.of(2022, 3, 14), null);
        Guest guest4 = new Guest("Abramov Nikita Alexandrovich", "7777 888888",
                LocalDate.of(2022, 3, 1),
                LocalDate.of(2022, 4, 1), null);
        Guest guest5 = new Guest("Yakovleva Margarita Vladimirovna", "9999 000000",
                LocalDate.of(2022, 3, 1),
                LocalDate.of(2022, 3, 14), null);
        guests.add(guest1);
        guests.add(guest2);
        guests.add(guest3);
        guests.add(guest4);
        guests.add(guest5);
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void addGuest(Guest guest) {
        if (guests.contains(guest)) {
            throw new KeyAlreadyExistsException("Guest already exists");
        }
        guests.add(guest);
    }

    public void addGuestToRoom(Guest guest, Room room) {
        if (!guests.contains(guest)) {
            guests.add(guest);
        }
        guest.setRoom(room);
        room.addGuest(guest);
    }

    public void removeGuest(Guest guest) {
        Room temp;
        try {
            temp = guest.getRoom();
        } catch (RuntimeException e) {
            guests.remove(guest); //if guest's room if null
            return;
        }
        temp.removeGuest(guest);
        guests.remove(guest);
    }

    public String getGuestsAsString(List<Guest> subList) {
        StringBuilder out = new StringBuilder();
        subList.forEach((guest) -> {
            out.append("Гость: ").append(guest.getFullName()).append("; Номер: ");
            try {
                out.append(guest.getRoom().getRoomNumber());
            } catch (NullPointerException e) {
                out.append("без номера");
            }
            out.append("; Дата заезда: ").append(guest.getCheckInDate())
                    .append("; Дата освобождения: ").append(guest.getCheckOutDate()).append("\n");
        });
        return out.toString();
    }

    public List<Guest> sortGuests(List<Guest> roomsTableToSort, Comparator<Guest> comparator) {
        List<Guest> sorted = new ArrayList<>();
        roomsTableToSort.stream().sorted(comparator)
                .forEach(sorted::add);
        return sorted;
    }

    public Guest getGuestByName(String fullName) throws NoSuchElementException {
        Guest result = guests.stream()
                .filter(guest -> (fullName.equals(guest.getFullName())))
                .findFirst().orElse(null);
        if (result == null) {
            throw new NoSuchElementException();
        }
        return result;
    }
}
