package task5.controller.action.maintenance;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.util.Scanner;

public class ChangeMaintenancePriceAction extends AbstractAction implements IAction {
    public ChangeMaintenancePriceAction (GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int maintenancePrice;
        int maintenanceId;
        Scanner scanner = new Scanner(System.in);
        try {
            maintenanceId = inputMaintenanceId();
            System.out.println("Введите новую стоимость услуги: ");
            maintenancePrice = scanner.nextInt();
            maintenanceService.getMaintenanceById(maintenanceId).setPrice(maintenancePrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}