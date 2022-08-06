package com.senla.service.impl;

import com.senla.controller.DTO.GuestDTO;
import com.senla.dao.GuestDao;
import com.senla.dao.RoomDao;
import com.senla.model.Guest;
import com.senla.model.Guest2Maintenance;
import com.senla.model.Role;
import com.senla.model.Room;
import com.senla.model.RoomStatus;
import com.senla.service.GuestService;
import com.senla.util.SortEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuestServiceImplTest {

    @Mock
    private GuestDao guestDao;
    @Mock
    private RoomDao roomDao;
    @InjectMocks
    private GuestServiceImpl guestService;

    @Test
    void createGuest_GuestRoomIsNull_GuestDaoCreateCalled() {
        Guest guest = mock(Guest.class);
        guestService.createGuest(guest);
        verify(guestDao).create(guest);
    }

    @Test
    void createGuest_GuestRoomNotNull_IllegalArgumentExceptionThrown() {
        Guest guest = new Guest();
        Room room = new Room();
        guest.setRoom(room);
        assertThrows(IllegalArgumentException.class, () -> guestService.createGuest(guest));
    }

    @Test
    void addGuestToRoom_RoomIsUnavailable_IllegalArgumentExceptionThrown() {
        Guest guest = new Guest();
        Room room = mock(Room.class);

        when(guestDao.getById(1)).thenReturn(guest);
        when(roomDao.getById(1)).thenReturn(room);
        when(room.isAvailableToSettle()).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> guestService.addGuestToRoom(1, 1, null, null));
    }

    @Test
    void addGuestToRoom_GuestHasAnyRoomAlready_IllegalArgumentExceptionThrown() {
        Room room1 = mock(Room.class);
        Room room2 = new Room();
        Guest guest = new Guest();

        guest.setRoom(room2);
        when(room1.isAvailableToSettle()).thenReturn(true);
        when(guestDao.getById(1)).thenReturn(guest);
        when(roomDao.getById(1)).thenReturn(room1);

        assertThrows(IllegalArgumentException.class, () -> guestService.addGuestToRoom(1, 1, null, null));
    }

    @Test
    void addGuestToRoom_IncorrectDates_IllegalArgumentExceptionThrown() {
        Room room = mock(Room.class);
        Guest guest = new Guest();
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.minusDays(1);

        when(room.isAvailableToSettle()).thenReturn(true);
        when(guestDao.getById(1)).thenReturn(guest);
        when(roomDao.getById(1)).thenReturn(room);

        assertThrows(IllegalArgumentException.class, () -> guestService.addGuestToRoom(1, 1, date1, date2));
    }

    @Test
    void addGuestToRoom_GuestWithoutRoomAndRoomAvailableAndCorrectDates_AddingToRoomAndUpdatingPriceAndSettingTime() {
        Room room = new Room();
        room.setRoomStatus(RoomStatus.FREE);
        room.setCapacity(2);
        room.setPrice(100);
        Guest guest1 = new Guest();
        Guest guest2 = new Guest();
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(1);

        when(guestDao.getById(1)).thenReturn(guest1);
        when(guestDao.getById(2)).thenReturn(guest2);
        when(roomDao.getById(1)).thenReturn(room);

        doAnswer(invocationOnMock -> {
            Room lambdaRoom = invocationOnMock.getArgument(0, Room.class);
            Guest lambdaGuest = invocationOnMock.getArgument(1, Guest.class);
            lambdaRoom.addGuest(lambdaGuest);
            return null;
        }).when(roomDao).addGuestToRoom(any(Room.class), any(Guest.class));

        doAnswer(invocationOnMock -> {
            Guest lambdaGuest = invocationOnMock.getArgument(0, Guest.class);
            Room lambdaRoom = invocationOnMock.getArgument(1, Room.class);
            lambdaGuest.setRoom(lambdaRoom);
            return null;
        }).when(guestDao).updateGuestRoom(any(Guest.class), any(Room.class));

        doAnswer(invocationOnMock -> {
            Guest lambdaGuest = invocationOnMock.getArgument(0, Guest.class);
            Integer lambdaPrice = invocationOnMock.getArgument(1, Integer.class);
            lambdaGuest.setPrice(lambdaPrice);
            return null;
        }).when(guestDao).updateGuestPrice(any(Guest.class), any(Integer.class));

        guestService.addGuestToRoom(1, 1, date1, date2);
        guestService.addGuestToRoom(2, 1, date1, date2);

        assertAll("Checking the states of two guests after adding to room." +
                        "If one fails - all other results will be described in the output also.",
                () -> assertEquals(guest1.getRoom(), room),
                () -> assertEquals(room.getCurrentGuestList().get(0), guest1),
                () -> assertEquals(guest1.getPrice(), room.getPrice()),
                () -> assertEquals(guest1.getCheckInDate(), date1),
                () -> assertEquals(guest1.getCheckOutDate(), date2),

                () -> assertEquals(guest2.getRoom(), room),
                () -> assertEquals(room.getCurrentGuestList().get(1), guest2),
                () -> assertEquals(guest2.getPrice(), 0),
                () -> assertEquals(guest2.getCheckInDate(), date1),
                () -> assertEquals(guest2.getCheckOutDate(), date2)
        );



    }

    @Test
    void removeGuestFromRoom_GuestDoesNotHaveRoom_DoNothing() {
        Guest guest = new Guest();
        when(guestDao.getById(1)).thenReturn(guest);
        guestService.removeGuestFromRoom(1);
    }

    @Test
    void removeGuestFromRoom_GuestHasRoom_RoomRemovedAndPriceUpdatedAndSettlementDatesNull() {
        Guest guest = new Guest();
        Room room = new Room();
        guest.setRoom(room);
        guest.setPrice(1000);
        room.setPrice(500);
        guest.setCheckInDate(LocalDate.now());
        guest.setCheckOutDate(LocalDate.now().plusDays(1));
        room.addGuest(guest);

        doAnswer(invocationOnMock -> {
            Room lambdaRoom = invocationOnMock.getArgument(0, Room.class);
            Guest lambdaGuest = invocationOnMock.getArgument(1, Guest.class);
            lambdaRoom.removeGuest(lambdaGuest);
            return null;
        }).when(roomDao).removeGuest(any(Room.class), any(Guest.class));

        doAnswer(invocationOnMock -> {
            Guest lambdaGuest = invocationOnMock.getArgument(0, Guest.class);
            Room lambdaRoom = invocationOnMock.getArgument(1, Room.class);
            lambdaGuest.setRoom(lambdaRoom);
            return null;
        }).when(guestDao).updateGuestRoom(any(Guest.class), nullable(Room.class));

        doAnswer(invocationOnMock -> {
            Guest lambdaGuest = invocationOnMock.getArgument(0, Guest.class);
            Integer lambdaPrice = invocationOnMock.getArgument(1, Integer.class);
            lambdaGuest.setPrice(lambdaPrice);
            return null;
        }).when(guestDao).updateGuestPrice(any(Guest.class), any(Integer.class));

        when(guestDao.getById(1)).thenReturn(guest);
        guestService.removeGuestFromRoom(1);

        assertNull(guest.getRoom());
        assertTrue(room.getCurrentGuestList().isEmpty());
        assertEquals(guest.getPrice(), 500);
        assertNull(guest.getCheckInDate());
        assertNull(guest.getCheckOutDate());
    }

    @Test
    void deleteGuest_GuestHasRoom_RemoveFromRoomAndDeleteCalled() {
        Guest guest = new Guest();
        Room room = new Room();
        guest.setRoom(room);
        guest.setCheckOutDate(LocalDate.now());
        room.addGuest(guest);

        doAnswer(invocationOnMock -> {
            Room lambdaRoom = invocationOnMock.getArgument(0, Room.class);
            Guest lambdaGuest = invocationOnMock.getArgument(1, Guest.class);
            lambdaRoom.removeGuest(lambdaGuest);
            return null;
        }).when(roomDao).removeGuest(any(Room.class), any(Guest.class));
        when(guestDao.getById(1)).thenReturn(guest);
        guestService.deleteGuest(1);

        assertTrue(room.getCurrentGuestList().isEmpty());
        verify(guestDao, times(1)).delete(guest);
    }

    @Test
    void getAllAmount() {
        guestService.getAllAmount();
        verify(guestDao, times(1)).getAllAmount();
    }

    @Test
    void getPriceByGuest() {
        Guest guest = new Guest();
        guest.setPrice(1000);

        when(guestDao.getById(1)).thenReturn(guest);

        assertEquals(guest.getPrice(), guestService.getPriceByGuest(1));
    }

    @Test
    void sortByAddition() {
        String order = "asc";
        SortEnum sort = SortEnum.BY_ADDITION;

        GuestService guestServiceSpy = spy(guestService);
        guestServiceSpy.sortByAddition(order);
        verify(guestServiceSpy, times(1)).getFieldToSortFromEnum(sort);
    }

    @Test
    void sortByAlphabet() {
        String order = "asc";
        SortEnum sort = SortEnum.BY_ALPHABET;

        GuestService guestServiceSpy = spy(guestService);
        guestServiceSpy.sortByAlphabet(order);
        verify(guestServiceSpy, times(1)).getFieldToSortFromEnum(sort);
    }

    @Test
    void sortByCheckOutDate() {
        String order = "asc";
        SortEnum sort = SortEnum.BY_CHECKOUT_DATE;

        GuestService guestServiceSpy = spy(guestService);
        guestServiceSpy.sortByCheckOutDate(order);
        verify(guestServiceSpy, times(1)).getFieldToSortFromEnum(sort);
    }

    @Test
    void testGetDefaultDao() {
        assertEquals(guestDao, guestService.getDefaultDao());
    }

    @Test
    public void getById() {
        Guest guest = new Guest();

        when(guestDao.getById(1)).thenReturn(guest);

        assertEquals(guest, guestService.getById(1));
        verify(guestDao, times(1)).getById(1);
        // Разницы ведь нет, что verify, что такой assertEquals?
        // Хотя если вдруг guest модифицируется как-то внутри getById сервиса, то assertEquals вернет exception.
        // Наверно оно лучше
    }

    @Test
    public void addAll() {
        List<Guest> arr = new ArrayList<>();

        for (int i = 0; i < (int) (Math.random() * 90 + 10); i++) {
            arr.add(new Guest());
        }

        guestService.addAll(arr);
        verify(guestDao, times(arr.size())).create(any(Guest.class));
    }

    @Test
    public void getAll() {
        List<Guest> arr = new ArrayList<>();

        for (int i = 0; i < (int) (Math.random() * 90 + 10); i++) {
            arr.add(new Guest());
        }

        when(guestDao.getAll("someField", "someOrder")).thenReturn(arr);

        assertEquals(arr, guestService.getAll("someField", "someOrder"));
    }

    @Test
    public void getGuest2MaintenanceOrderTime() {
        List<Guest2Maintenance> arr = new ArrayList<>();

        //Тестирую с рандомным временем
        ZoneOffset zoneOffset = ZoneId.of("Europe/Moscow").getRules().getOffset(LocalDateTime.now());
        for (int i = 0; i < (int) (Math.random() * 90 + 10); i++) {
            long minTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0).toEpochSecond(zoneOffset);
            long maxTime = LocalDateTime.now().toEpochSecond(zoneOffset);
            long randomTime = ThreadLocalRandom.current().nextLong(minTime, maxTime);
            arr.add(new Guest2Maintenance(1L, 1L, LocalDateTime.ofEpochSecond(randomTime, 0, zoneOffset)));
        }

        List<LocalDateTime> expectedResult = arr.stream()
                .map(Guest2Maintenance::getOrderTime)
                .collect(Collectors.toList());

        when(guestDao.getGuest2Maintenance(1, 1)).thenReturn(arr);

        assertEquals(expectedResult, guestService.getGuest2MaintenanceOrderTime(1, 1));
        //two lists are defined to be equal if they contain the same elements in the same order.
    }

    @Test
    public void getFieldToSortFromEnum() {
        assertAll(
                () -> assertEquals("id", guestService.getFieldToSortFromEnum(SortEnum.BY_ADDITION)),
                () -> assertEquals("price", guestService.getFieldToSortFromEnum(SortEnum.BY_PRICE)),
                () -> assertEquals("category", guestService.getFieldToSortFromEnum(SortEnum.BY_CATEGORY)),
                () -> assertEquals("orderTime", guestService.getFieldToSortFromEnum(SortEnum.BY_TIME)),
                () -> assertEquals("name", guestService.getFieldToSortFromEnum(SortEnum.BY_ALPHABET)),
                () -> assertEquals("checkOutDate", guestService.getFieldToSortFromEnum(SortEnum.BY_CHECKOUT_DATE)),
                () -> assertEquals("capacity", guestService.getFieldToSortFromEnum(SortEnum.BY_CAPACITY)),
                () -> assertEquals("starsNumber", guestService.getFieldToSortFromEnum(SortEnum.BY_STARS))
        );
    }

    @Test
    public void updateEntityFromDto() {
        Guest guest1 = Guest.builder()
                .name("user")
                .passport("passport")
                .role(Role.USER)
                .price(99)
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.now().plusDays(2))
                .build();
        Guest guest2 = Guest.builder().build();

        GuestDTO guestDTO = GuestDTO.builder()
                .name("user")
                .passport("passport")
                .role(Role.USER)
                .price(99)
                .roomId(null)
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.now().plusDays(2))
                .build();

        guestService.updateEntityFromDto(guest2, guestDTO, Guest.class);
        assertTrue(new ReflectionEquals(guest1, "").matches(guest2)); //by-field comparing
    }
}