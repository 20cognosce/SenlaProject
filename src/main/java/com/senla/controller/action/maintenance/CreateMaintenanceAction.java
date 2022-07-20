package com.senla.controller.action.maintenance;

import com.senla.controller.action.AbstractAction;
import com.senla.controller.action.ConsoleReaderUtil;
import com.senla.model.MaintenanceCategory;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import com.senla.service.RoomService;


public class CreateMaintenanceAction extends AbstractAction {
    public CreateMaintenanceAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        System.out.println("Введите наименование услуги: ");
        String name = ConsoleReaderUtil.readLine();
        System.out.println("Введите цену: ");
        int price = ConsoleReaderUtil.readInteger();
        MaintenanceCategory maintenanceCategory = ConsoleReaderUtil.inputMaintenanceCategory();

        getMaintenanceService().createMaintenance(name, price, maintenanceCategory);
    }
}