package com.senla.controller.action.maintenance;

import com.senla.controller.action.AbstractAction;
import com.senla.controller.action.ConsoleReaderUtil;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import com.senla.service.RoomService;
import lombok.SneakyThrows;

public class OrderMaintenanceAction extends AbstractAction {
    public OrderMaintenanceAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @SneakyThrows
    @Override
    public void execute() {
        long guestId = ConsoleReaderUtil.inputId("Введите идентификатор гостя: ");
        long maintenanceId = ConsoleReaderUtil.inputId("Введите идентификатор услуги: ");
        getMaintenanceService().executeMaintenance(guestId, maintenanceId);
    }
}