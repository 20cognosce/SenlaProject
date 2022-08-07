package com.senla.service.impl;

import com.senla.dao.GuestDao;
import com.senla.dao.MaintenanceDao;
import com.senla.model.Guest;
import com.senla.model.Maintenance;
import com.senla.service.MaintenanceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MaintenanceServiceImplTest {

    @Mock
    private GuestDao guestDao;
    @Mock
    private MaintenanceDao maintenanceDao;
    @InjectMocks
    private MaintenanceServiceImpl maintenanceService;

    @Test
    void createMaintenance() {
        Maintenance maintenance = new Maintenance();
        maintenanceService.createMaintenance(maintenance);
        verify(maintenanceDao, times(1)).create(maintenance);
    }

    @Test
    void updateMaintenance() {
        Maintenance maintenance = new Maintenance();
        maintenanceService.updateMaintenance(maintenance);
        verify(maintenanceDao, times(1)).update(maintenance);
    }

    @Test
    void executeMaintenance() {
        Guest guest = new Guest();
        Maintenance maintenance = new Maintenance();
        maintenance.setPrice(500);

        when(guestDao.getById(1)).thenReturn(guest);
        when(maintenanceDao.getById(1)).thenReturn(maintenance);

        doAnswer(invocationOnMock -> {
            guest.setOrderedMaintenances(List.of(maintenance));
            return null;
        }).when(maintenanceDao).addOrderedMaintenance(1L, 1L);

        doAnswer(invocationOnMock -> {
            Guest guest1 = invocationOnMock.getArgument(0, Guest.class);
            Integer price = invocationOnMock.getArgument(1, Integer.class);
            guest1.setPrice(price);
            return null;
        }).when(guestDao).updateGuestPrice(any(Guest.class), anyInt());

        maintenanceService.executeMaintenance(1, 1);
        Assertions.assertEquals(guest.getOrderedMaintenances(), List.of(maintenance));
        Assertions.assertEquals(guest.getPrice(), maintenance.getPrice());
    }

    @Test
    void sortByAddition() {
        String order = "asc";

        MaintenanceService maintenanceServiceSpy = spy(maintenanceService);
        maintenanceServiceSpy.sortByAddition(order);
        verify(maintenanceServiceSpy, times(1)).getAll("id", order);
    }

    @Test
    void sortByPrice() {
        String order = "asc";

        MaintenanceService maintenanceServiceSpy = spy(maintenanceService);
        maintenanceServiceSpy.sortByPrice(order);
        verify(maintenanceServiceSpy, times(1)).getAll("price", order);
    }

    @Test
    void sortByCategory() {
        String order = "asc";

        MaintenanceService maintenanceServiceSpy = spy(maintenanceService);
        maintenanceServiceSpy.sortByCategory(order);
        verify(maintenanceServiceSpy, times(1)).getAll("category", order);
    }

    @Test
    void sortMaintenancesOfGuestByAddition() {
        String order = "asc";

        maintenanceService.sortMaintenancesOfGuestByAddition(1, order);
        verify(maintenanceDao, times(1)).getMaintenancesOfGuestSorted(1L, "id", order);
    }

    @Test
    void sortMaintenancesOfGuestByPrice() {
        String order = "asc";

        maintenanceService.sortMaintenancesOfGuestByPrice(1, order);
        verify(maintenanceDao, times(1)).getMaintenancesOfGuestSorted(1L, "price", order);
    }

    @Test
    void sortMaintenancesOfGuestByTime() {
        String order = "asc";

        maintenanceService.sortMaintenancesOfGuestByTime(1, order);
        verify(maintenanceDao, times(1)).getMaintenancesOfGuestSorted(1L, "orderTime", order);
    }

    @Test
    void testGetDefaultDao() {
        Assertions.assertEquals(maintenanceDao, maintenanceService.getDefaultDao());
    }
}
