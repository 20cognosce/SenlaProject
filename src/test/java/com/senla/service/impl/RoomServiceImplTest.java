package com.senla.service.impl;

import com.senla.dao.RoomDao;
import com.senla.model.Guest;
import com.senla.model.Room;
import com.senla.model.RoomStatus;
import com.senla.service.RoomService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {

    @Mock
    private RoomDao roomDao;
    @InjectMocks
    private RoomServiceImpl roomService;
    
    @Test
    void getLastNGuests() {
        Room room = new Room();
        Guest guest1 = Guest.builder().checkInDate(LocalDate.now()).build();
        Guest guest2 = Guest.builder().checkInDate(LocalDate.now().plusDays(1)).build();
        Guest guest3 = Guest.builder().checkInDate(LocalDate.now().plusDays(2)).build();

        room.addGuest(guest1);
        room.addGuest(guest2);
        room.addGuest(guest3);
        
        when(roomDao.getGuestList(1L)).thenReturn(room.getCurrentGuestList());
        roomService.lastNGuests = 2;

        assertEquals(Arrays.asList(guest3, guest2), roomService.getLastNGuests(1));
    }

    @Test
    void getGuestsList() {
        Room room = new Room();
        Guest guest1 = new Guest();
        Guest guest2 = new Guest();
        Guest guest3 = new Guest();

        room.addGuest(guest1);
        room.addGuest(guest2);
        room.addGuest(guest3);

        when(roomDao.getGuestList(1L)).thenReturn(room.getCurrentGuestList());
        assertEquals(Arrays.asList(guest1, guest2, guest3), roomService.getGuestsList(1));
    }

    @Test
    void createRoom() {
        Room room = new Room();
        roomService.createRoom(room);
        verify(roomDao, times(1)).create(room);
    }

    @Test
    void updateRoom_ChangingRoomStatusInUnavailable_ServiceUnavailableExceptionThrown() {
        roomService.changeRoomStatusPossibility = "no";
        Room room1 = Room.builder().roomStatus(RoomStatus.FREE).build();
        Room room2 = Room.builder().roomStatus(RoomStatus.CLEANING).build();
        room2.setId(1);

        when(roomDao.getById(1)).thenReturn(room1);

        Exception exception = new Exception();
        try {
            roomService.updateRoom(room2);
        } catch (ServiceUnavailableException e) {
            exception = e;
        }

        assertEquals(ServiceUnavailableException.class, exception.getClass());
    }

    @SneakyThrows
    @Test
    void updateRoom_ChangingRoomStatusInAvailable_RoomUpdated() {
        Room detachedRoom = Room.builder().roomStatus(RoomStatus.FREE).build();
        Room updatedRoom = Room.builder().roomStatus(RoomStatus.CLEANING).build();
        updatedRoom.setId(1);

        when(roomDao.getById(1)).thenReturn(detachedRoom);

        roomService.updateRoom(updatedRoom);
        verify(roomDao, times(1)).update(updatedRoom);
    }

    @Test
    void updateRoomDetails() {
        String details = "details";
        Room room = new Room();

        when(roomDao.getById(1)).thenReturn(room);

        roomService.updateRoomDetails(1, details);
        assertEquals(details, room.getDetails());
    }

    @Test
    void sortByAddition() {
        String order = "asc";

        RoomService roomServiceSpy = spy(roomService);
        roomServiceSpy.sortByAddition(order);
        verify(roomServiceSpy, times(1)).getAll("id", order);
    }

    @Test
    void sortByCapacity() {
        String order = "asc";

        RoomService roomServiceSpy = spy(roomService);
        roomServiceSpy.sortByCapacity(order);
        verify(roomServiceSpy, times(1)).getAll("capacity", order);
    }

    @Test
    void sortByPrice() {
        String order = "asc";

        RoomService roomServiceSpy = spy(roomService);
        roomServiceSpy.sortByPrice(order);
        verify(roomServiceSpy, times(1)).getAll("price", order);
    }

    @Test
    void sortByStars() {
        String order = "asc";

        RoomService roomServiceSpy = spy(roomService);
        roomServiceSpy.sortByStars(order);
        verify(roomServiceSpy, times(1)).getAll("starsNumber", order);
    }

    @Test
    void getFree() {
        LocalDate asAtSpecificDate = LocalDate.now();
        String order = "asc";

        roomService.getFree(asAtSpecificDate, order);
        verify(roomDao, times(1)).getFreeRoomsByDateSorted(asAtSpecificDate,"id", order);
    }

    @Test
    void getFreeAmount() {
        LocalDate asAtSpecificDate = LocalDate.now();
        roomService.getFreeAmount(asAtSpecificDate);
        verify(roomDao, times(1)).getFreeRoomsAmount(asAtSpecificDate);
    }

    @Test
    void sortFreeRoomsByAddition() {
        LocalDate asAtSpecificDate = LocalDate.now();
        String order = "asc";

        roomService.getFree(asAtSpecificDate, order);
        verify(roomDao, times(1)).getFreeRoomsByDateSorted(asAtSpecificDate,"id", order);
    }

    @Test
    void sortFreeRoomsByCapacity() {
        LocalDate asAtSpecificDate = LocalDate.now();
        String order = "asc";

        roomService.sortFreeRoomsByCapacity(asAtSpecificDate, order);
        verify(roomDao, times(1)).getFreeRoomsByDateSorted(asAtSpecificDate,"capacity", order);
    }

    @Test
    void sortFreeRoomsByPrice() {
        LocalDate asAtSpecificDate = LocalDate.now();
        String order = "asc";

        roomService.sortFreeRoomsByPrice(asAtSpecificDate, order);
        verify(roomDao, times(1)).getFreeRoomsByDateSorted(asAtSpecificDate,"price", order);

    }

    @Test
    void sortFreeRoomsByStars() {
        LocalDate asAtSpecificDate = LocalDate.now();
        String order = "asc";

        roomService.sortFreeRoomsByStars(asAtSpecificDate, order);
        verify(roomDao, times(1)).getFreeRoomsByDateSorted(asAtSpecificDate,"starsNumber", order);
    }

    @Test
    void testGetDefaultDao() {
        assertEquals(roomDao, roomService.getDefaultDao());
    }
}
