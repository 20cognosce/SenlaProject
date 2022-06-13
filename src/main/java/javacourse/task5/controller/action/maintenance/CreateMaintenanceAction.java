package javacourse.task5.controller.action.maintenance;

import javacourse.task5.controller.action.AbstractAction;
import javacourse.task5.controller.action.ConsoleReaderUtil;
import javacourse.task5.dao.entity.MaintenanceCategory;
import javacourse.task5.service.GuestService;
import javacourse.task5.service.MaintenanceService;
import javacourse.task5.service.RoomService;


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