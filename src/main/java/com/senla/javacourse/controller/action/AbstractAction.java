package com.senla.javacourse.controller.action;

import com.senla.javacourse.controller.IAction;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.RoomService;

public abstract class AbstractAction implements IAction {
    private final GuestService guestService;
    private final RoomService roomService;
    private final MaintenanceService maintenanceService;

    public AbstractAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        this.guestService = guestService;
        this.roomService = roomService;
        this.maintenanceService = maintenanceService;
    }

    public GuestService getGuestService() {
        return guestService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public MaintenanceService getMaintenanceService() {
        return maintenanceService;
    }
}
