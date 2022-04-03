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
        String name;
        int maintenanceCategoryIndex = -1;
        int price;
        Scanner scanner = new Scanner(System.in);
        ArrayList<MaintenanceCategory> maintenanceCategories;

        //Я догадываюсь, что это очень плохо что я обращаюсь к уровню модел из UI, но стоило пилить сервис для енама?
        try {
            System.out.println("Введите наименование услуги: ");
            name = scanner.nextLine();
            System.out.println("Введите цену: ");
            price = scanner.nextInt();
            System.out.println("Выберите текущий статус номера (индекс): ");

            maintenanceCategories = new ArrayList<>(Arrays.asList(MaintenanceCategory.values()));
            int i = 1;

            while (i < maintenanceCategories.size()) {
                System.out.println(i + ". " + maintenanceCategories.get(i - 1));
                ++i;
            }

            while (maintenanceCategoryIndex < 1 || maintenanceCategoryIndex > maintenanceCategories.size()) {
                maintenanceCategoryIndex = scanner.nextInt();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        maintenanceService.createMaintenance(name, price, maintenanceCategories.get(maintenanceCategoryIndex - 1));
    }
}