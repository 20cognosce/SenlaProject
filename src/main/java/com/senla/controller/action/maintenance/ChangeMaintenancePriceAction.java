package com.senla.controller.action.maintenance;

import com.senla.controller.action.AbstractAction;
import com.senla.controller.action.ConsoleReaderUtil;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import com.senla.service.RoomService;

public class ChangeMaintenancePriceAction extends AbstractAction {
    public ChangeMaintenancePriceAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long maintenanceId = ConsoleReaderUtil.inputId("Введите идентификатор услуги: ");
        System.out.println("Введите новую стоимость услуги: ");
        int maintenancePrice = ConsoleReaderUtil.readInteger();
        getMaintenanceService().updateMaintenancePrice(maintenanceId, maintenancePrice);
    }
}