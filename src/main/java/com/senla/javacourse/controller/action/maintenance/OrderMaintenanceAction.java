package com.senla.javacourse.controller.action.maintenance;

import com.senla.javacourse.controller.action.ConsoleReaderUtil;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.controller.action.AbstractAction;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.RoomService;

public class OrderMaintenanceAction extends AbstractAction  {
    public OrderMaintenanceAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long guestId = ConsoleReaderUtil.inputId("Введите идентификатор гостя: ");
        long maintenanceId = ConsoleReaderUtil.inputId("Введите идентификатор услуги: ");
        getMaintenanceService().executeMaintenance(guestId, maintenanceId);
    }
}
