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

        List<MenuItem> guestsSortingMenuItemsList = new ArrayList<>();
        List<MenuItem> maintenanceOfGuestSortingMenuItemList = new ArrayList<>();
        List<MenuItem> roomSortingMenuItemList = new ArrayList<>();
        List<MenuItem> roomFreeSortingMenuItemList = new ArrayList<>();
        List<MenuItem> maintenanceSortingMenuItemsList = new ArrayList<>();

        Menu guestSortingMenu = new Menu("Сортировать список гостей", guestsSortingMenuItemsList);
        Menu roomSortingMenu = new Menu("Сортировать список номеров", roomSortingMenuItemList);
        Menu roomFreeSortingMenu = new Menu("Сортировать список свободных номеров", roomFreeSortingMenuItemList);
        Menu maintenancesOfGuestSortingMenu = new Menu("Сортировать список услуг гостя", maintenanceOfGuestSortingMenuItemList);
        Menu maintenanceSortingMenu = new Menu("Сортировать список услуг", maintenanceSortingMenuItemsList);

        rootMenuList.add(new MenuItem("Гости", null, guestMenu, null));
        rootMenuList.add(new MenuItem("Номера", null, roomMenu, null));
        rootMenuList.add(new MenuItem("Обслуживание", null, maintenanceMenu, null));

        guestMenuItemsList.add(new MenuItem("Добавить гостя", Hotel.AddGuestToDatabaseAction.getInstance(), null, rootMenu));
        guestMenuItemsList.add(new MenuItem("Поселить в номер", Hotel.AddGuestToRoomAction.getInstance(), null, rootMenu));
        guestMenuItemsList.add(new MenuItem("Удалить гостя", Hotel.RemoveGuestFromDatabaseAction.getInstance(),null, rootMenu));
        guestMenuItemsList.add(new MenuItem("Выселить из номера", Hotel.RemoveGuestFromRoomAction.getInstance(),null, rootMenu));
        guestMenuItemsList.add(new MenuItem("Общее количество гостей", Hotel.PrintGuestsNumberAction.getInstance(),null, rootMenu));
        guestMenuItemsList.add(new MenuItem("Заказать обслуживание", Hotel.OrderGuestMaintenanceAction.getInstance(), null, roomMenu));
        guestMenuItemsList.add(new MenuItem("Сумма оплаты гостя", Hotel.PrintGuestPaymentAction.getInstance(),null, rootMenu));
        guestMenuItemsList.add(new MenuItem("Список гостей", Hotel.PrintGuestsAction.getInstance(), guestSortingMenu, rootMenu));
        guestMenuItemsList.add(new MenuItem("Полная информация о госте", Hotel.PrintGuestByNameAction.getInstance(),null, rootMenu));

        roomMenuItemsList.add(new MenuItem("Добавить номер", Hotel.AddRoomToDatabaseAction.getInstance(), null, rootMenu));
        roomMenuItemsList.add(new MenuItem("Изменить цену", Hotel.ChangeRoomPriceAction.getInstance(), null, rootMenu));
        roomMenuItemsList.add(new MenuItem("Изменить статус", Hotel.ChangeRoomStatusAction.getInstance(), null, rootMenu));
        roomMenuItemsList.add(new MenuItem("Посмотреть детали", Hotel.PrintRoomDetailsAction.getInstance(), null, rootMenu));
        roomMenuItemsList.add(new MenuItem("Посмотреть N последних гостей", Hotel.PrintNLastGuestsAction.getInstance(), null, rootMenu));
        roomMenuItemsList.add(new MenuItem("Общее число свободных номеров", Hotel.PrintFreeRoomsNumberAction.getInstance(), null, rootMenu));
        roomMenuItemsList.add(new MenuItem("Список свободных номеров по дате", Hotel.PrintFreeRoomsAction.getInstance(), roomFreeSortingMenu, rootMenu));
        roomMenuItemsList.add(new MenuItem("Список номеров", Hotel.PrintRoomsAction.getInstance(), roomSortingMenu, rootMenu));
        roomMenuItemsList.add(new MenuItem("Полная информация о номере", Hotel.PrintRoomByNumberAction.getInstance(), null, rootMenu));

        maintenanceMenuItemsList.add(new MenuItem("Добавить услугу", Hotel.AddMaintenanceAction.getInstance(), null, rootMenu));
        maintenanceMenuItemsList.add(new MenuItem("Изменить цену", Hotel.ChangeMaintenancePriceAction.getInstance(), null, rootMenu));
        maintenanceMenuItemsList.add(new MenuItem("Список услуг", Hotel.PrintMaintenancesAction.getInstance(), maintenanceSortingMenu, rootMenu));
        maintenanceMenuItemsList.add(new MenuItem("Услуги гостя", Hotel.PrintMaintenancesOfGuestAction.getInstance(), maintenancesOfGuestSortingMenu, rootMenu));
        maintenanceMenuItemsList.add(new MenuItem("Полная информация об услуге", Hotel.PrintMaintenanceByNameAction.getInstance(),null, rootMenu));

        guestsSortingMenuItemsList.add(new MenuItem("Без сортировки", Hotel.PrintGuestsSortedNot.getInstance(), null, guestMenu));
        guestsSortingMenuItemsList.add(new MenuItem("Сортировать по алфавиту", Hotel.PrintGuestsSortedAlphabet.getInstance(), null, guestMenu));
        guestsSortingMenuItemsList.add(new MenuItem("Сортировать по дате выезда", Hotel.PrintGuestsSortedByCheckoutDate.getInstance(), null, guestMenu));
        roomSortingMenuItemList.add(new MenuItem("Без сортировки", Hotel.PrintRoomsSortedNot.getInstance(), null, roomMenu));
        roomSortingMenuItemList.add(new MenuItem("Сортировать по цене", Hotel.PrintRoomsSortedByPrice.getInstance(), null, roomMenu));
        roomSortingMenuItemList.add(new MenuItem("Сортировать по вместимости", Hotel.PrintRoomsSortedByCapacity.getInstance(), null, roomMenu));
        roomSortingMenuItemList.add(new MenuItem("Сортировать по количеству звёзд", Hotel.PrintRoomsSortedByStars.getInstance(), null, roomMenu));
        roomFreeSortingMenuItemList.add(new MenuItem("Без сортировки", Hotel.PrintFreeRoomsSortedNot.getInstance(), null, roomMenu));
        roomFreeSortingMenuItemList.add(new MenuItem("Сортировать по цене", Hotel.PrintFreeRoomsSortedByPrice.getInstance(), null, roomMenu));
        roomFreeSortingMenuItemList.add(new MenuItem("Сортировать по вместимости", Hotel.PrintFreeRoomsSortedByCapacity.getInstance(), null, roomMenu));
        roomFreeSortingMenuItemList.add(new MenuItem("Сортировать по количеству звёзд", Hotel.PrintFreeRoomsSortedByStars.getInstance(), null, roomMenu));
        maintenanceOfGuestSortingMenuItemList.add(new MenuItem("Без сортировки", Hotel.PrintMaintenancesOfGuestSortedNot.getInstance(), null, maintenanceMenu));
        maintenanceOfGuestSortingMenuItemList.add(new MenuItem("Сортировать по цене", Hotel.PrintMaintenancesOfGuestSortedByPrice.getInstance(), null,maintenanceMenu));
        maintenanceOfGuestSortingMenuItemList.add(new MenuItem("Сортировать по времени", Hotel.PrintMaintenancesOfGuestSortedByTime.getInstance(), null, maintenanceMenu));
        maintenanceSortingMenuItemsList.add(new MenuItem("Без сортировки", Hotel.PrintMaintenancesSortedNot.getInstance(), null, maintenanceMenu));
        maintenanceSortingMenuItemsList.add(new MenuItem("Сортировать по цене", Hotel.PrintMaintenancesSortedByPrice.getInstance(), null,maintenanceMenu));
        maintenanceSortingMenuItemsList.add(new MenuItem("Сортировать по категориям", Hotel.PrintMaintenancesSortedByCategory.getInstance(), null, maintenanceMenu));
    }

    Menu getRootMenu() {
        return rootMenu;
    }
}
