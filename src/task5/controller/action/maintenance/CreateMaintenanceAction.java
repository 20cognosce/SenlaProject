package task5.controller.action.maintenance;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.dao.model.MaintenanceCategory;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class CreateMaintenanceAction extends AbstractAction implements IAction {
    public CreateMaintenanceAction (GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int maintenanceCategoryIndex = -1;
        Scanner scanner = new Scanner(System.in);
        MaintenanceCategory[] maintenanceCategories = MaintenanceCategory.values();

        //Я догадываюсь, что это очень плохо что я обращаюсь к уровню модел из UI, но стоило пилить сервис для енама?
        System.out.println("Введите наименование услуги: ");
        String name = scanner.nextLine();
        System.out.println("Введите цену: ");
        int price = scanner.nextInt();

        System.out.println("Выберите текущий статус номера (индекс): ");
        int i = 0;
        while (i < maintenanceCategories.length) {
            System.out.println(i + 1 + ". " + maintenanceCategories[i]);
            ++i;
        }
        while (maintenanceCategoryIndex < 1 || maintenanceCategoryIndex > maintenanceCategories.length) {
            maintenanceCategoryIndex = scanner.nextInt();
        }

        maintenanceService.createMaintenance(name, price, maintenanceCategories[maintenanceCategoryIndex - 1]);
    }
}