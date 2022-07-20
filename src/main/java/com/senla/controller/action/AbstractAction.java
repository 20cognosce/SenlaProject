package com.senla.controller.action;

import com.senla.controller.IAction;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import com.senla.service.RoomService;

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
