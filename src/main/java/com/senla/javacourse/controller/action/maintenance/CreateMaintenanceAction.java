package com.senla.javacourse.controller.action.maintenance;

import com.senla.javacourse.controller.action.ConsoleReaderUtil;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.controller.action.AbstractAction;
import com.senla.javacourse.dao.entity.MaintenanceCategory;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.RoomService;


public class CreateMaintenanceAction extends AbstractAction  {
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