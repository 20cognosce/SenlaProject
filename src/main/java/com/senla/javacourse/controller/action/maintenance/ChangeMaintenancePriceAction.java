package com.senla.javacourse.controller.action.maintenance;

import com.senla.javacourse.controller.action.AbstractAction;
import com.senla.javacourse.controller.action.ConsoleReaderUtil;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.service.RoomService;

public class ChangeMaintenancePriceAction extends AbstractAction  {
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