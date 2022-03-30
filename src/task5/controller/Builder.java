package task5.controller;

import task5.service.Hotel;

import java.util.ArrayList;
import java.util.List;

public class Builder {
    private Menu rootMenu;

    void buildMenu() {
        List<MenuItem> rootMenuList = new ArrayList<>();
        rootMenu = new Menu ("Главное меню управления отелем", rootMenuList);

        List<MenuItem> guestMenuItemsList = new ArrayList<>();
        List<MenuItem> roomMenuItemsList = new ArrayList<>();
        List<MenuItem> maintenanceMenuItemsList = new ArrayList<>();

        Menu guestMenu = new Menu ("Меню управления гостями", guestMenuItemsList);
        Menu roomMenu = new Menu ("Меню управления номерами", roomMenuItemsList);
        Menu maintenanceMenu = new Menu ("Меню управления обслуживанием", maintenanceMenuItemsList);

        guestMenuItemsList.add(new MenuItem("Добавить гостя", Hotel.AddGuestToDatabaseAction.getInstance(),
                null, rootMenu));
        guestMenuItemsList.add(new MenuItem("Поселить в номер", Hotel.AddGuestToRoomAction.getInstance(),
                null, rootMenu));
        guestMenuItemsList.add(new MenuItem("Список гостей", Hotel.PrintGuestsAction.getInstance(),
                null, rootMenu));
        roomMenuItemsList.add(new MenuItem("Список номеров", Hotel.PrintRoomsAction.getInstance(),
                null, rootMenu));

        rootMenuList.add(new MenuItem("Гости", null, guestMenu, null));
        rootMenuList.add(new MenuItem("Номера", null, roomMenu, null));
        rootMenuList.add(new MenuItem("Обслуживание", null, maintenanceMenu, null));
    };

    Menu getRootMenu() {
        return rootMenu;
    }
}
