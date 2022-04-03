package task5.controller;

import task5.controller.action.guest.*;
import task5.controller.action.guest.PrintAllAction;
import task5.controller.action.guest.PrintAllSortedNot;
import task5.controller.action.maintenance.*;
import task5.controller.action.maintenance.PrintAllSortedByPrice;
import task5.controller.action.room.*;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.util.ArrayList;
import java.util.List;

public class Builder {
    private final GuestService guestService;
    private final RoomService roomService;
    private final MaintenanceService maintenanceService;
    private Menu rootMenu;

    public Builder (GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        this.guestService = guestService;
        this.roomService = roomService;
        this.maintenanceService = maintenanceService;
    }

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

        guestMenuItemsList.add(new MenuItem("Добавить гостя", new CreateGuestAction(guestService, roomService, maintenanceService), null, rootMenu));
        guestMenuItemsList.add(new MenuItem("Поселить в номер", new AddGuestToRoomAction(guestService, roomService, maintenanceService), null, rootMenu));
        guestMenuItemsList.add(new MenuItem("Удалить гостя", new DeleteGuestAction(guestService, roomService, maintenanceService),null, rootMenu));
        guestMenuItemsList.add(new MenuItem("Выселить из номера", new RemoveGuestFromRoomAction(guestService, roomService, maintenanceService),null, rootMenu));
        guestMenuItemsList.add(new MenuItem("Общее количество гостей", new PrintAllAmountAction(guestService, roomService, maintenanceService),null, rootMenu));
        guestMenuItemsList.add(new MenuItem("Сумма оплаты гостя", new PrintGuestPaymentAction(guestService, roomService, maintenanceService),null, rootMenu));
        guestMenuItemsList.add(new MenuItem("Список гостей", new PrintAllAction(guestService, roomService, maintenanceService), guestSortingMenu, rootMenu));
        guestsSortingMenuItemsList.add(new MenuItem("Без сортировки", new PrintAllSortedNot(guestService, roomService, maintenanceService), null, guestMenu));
        guestsSortingMenuItemsList.add(new MenuItem("Сортировать по алфавиту", new PrintAllSortedAlphabet(guestService, roomService, maintenanceService), null, guestMenu));
        guestsSortingMenuItemsList.add(new MenuItem("Сортировать по дате выезда", new PrintAllSortedByCheckoutDate(guestService, roomService, maintenanceService), null, guestMenu));
        guestMenuItemsList.add(new MenuItem("Полная информация о госте", new PrintGuestByIdAction(guestService, roomService, maintenanceService),null, rootMenu));

        roomMenuItemsList.add(new MenuItem("Добавить номер", new CreateRoomAction(guestService, roomService, maintenanceService), null, rootMenu));
        roomMenuItemsList.add(new MenuItem("Изменить цену", new ChangeRoomPriceAction(guestService, roomService, maintenanceService), null, rootMenu));
        roomMenuItemsList.add(new MenuItem("Изменить статус", new ChangeRoomPriceAction(guestService, roomService, maintenanceService), null, rootMenu));
        roomMenuItemsList.add(new MenuItem("Посмотреть детали", new PrintRoomDetailsAction(guestService, roomService, maintenanceService), null, rootMenu));
        roomMenuItemsList.add(new MenuItem("Посмотреть N последних гостей", new PrintNLastGuestsAction(guestService, roomService, maintenanceService), null, rootMenu));
        roomMenuItemsList.add(new MenuItem("Общее число свободных номеров", new PrintFreeRoomsAmountAction(guestService, roomService, maintenanceService), null, rootMenu));
        roomMenuItemsList.add(new MenuItem("Список свободных номеров по дате", new PrintFreeRoomsAction(guestService, roomService, maintenanceService), roomFreeSortingMenu, rootMenu));
        roomFreeSortingMenuItemList.add(new MenuItem("Без сортировки", new PrintFreeRoomsSortedNot(guestService, roomService, maintenanceService), null, roomMenu));
        roomFreeSortingMenuItemList.add(new MenuItem("Сортировать по цене", new PrintFreeRoomsSortedByPrice(guestService, roomService, maintenanceService), null, roomMenu));
        roomFreeSortingMenuItemList.add(new MenuItem("Сортировать по вместимости", new PrintFreeRoomsSortedByCapacity(guestService, roomService, maintenanceService), null, roomMenu));
        roomFreeSortingMenuItemList.add(new MenuItem("Сортировать по количеству звёзд", new PrintFreeRoomsSortedByStars(guestService, roomService, maintenanceService), null, roomMenu));
        roomMenuItemsList.add(new MenuItem("Список номеров", new PrintAllAction(guestService, roomService, maintenanceService), roomSortingMenu, rootMenu));
        roomSortingMenuItemList.add(new MenuItem("Без сортировки", new task5.controller.action.maintenance.PrintAllSortedNot(guestService, roomService, maintenanceService), null, roomMenu));
        roomSortingMenuItemList.add(new MenuItem("Сортировать по цене", new task5.controller.action.room.PrintAllSortedByPrice(guestService, roomService, maintenanceService), null, roomMenu));
        roomSortingMenuItemList.add(new MenuItem("Сортировать по вместимости", new PrintAllSortedByCapacity(guestService, roomService, maintenanceService), null, roomMenu));
        roomSortingMenuItemList.add(new MenuItem("Сортировать по количеству звёзд", new PrintAllSortedByStars(guestService, roomService, maintenanceService), null, roomMenu));
        roomMenuItemsList.add(new MenuItem("Полная информация о номере", new PrintRoomByIdAction(guestService, roomService, maintenanceService), null, rootMenu));

        maintenanceMenuItemsList.add(new MenuItem("Добавить услугу", new CreateMaintenanceAction(guestService, roomService, maintenanceService), null, rootMenu));
        maintenanceMenuItemsList.add(new MenuItem("Изменить цену", new ChangeMaintenancePriceAction(guestService, roomService, maintenanceService), null, rootMenu));
        maintenanceMenuItemsList.add(new MenuItem("Список услуг", new task5.controller.action.maintenance.PrintAllSortedNot(guestService, roomService, maintenanceService), maintenanceSortingMenu, rootMenu));
        maintenanceSortingMenuItemsList.add(new MenuItem("Без сортировки", new task5.controller.action.maintenance.PrintAllSortedNot(guestService, roomService, maintenanceService), null, maintenanceMenu));
        maintenanceSortingMenuItemsList.add(new MenuItem("Сортировать по цене", new PrintAllSortedByPrice(guestService, roomService, maintenanceService), null,maintenanceMenu));
        maintenanceSortingMenuItemsList.add(new MenuItem("Сортировать по категориям", new PrintAllSortedByCategory(guestService, roomService, maintenanceService), null, maintenanceMenu));
        maintenanceMenuItemsList.add(new MenuItem("Заказать обслуживание", new OrderMaintenanceAction(guestService, roomService, maintenanceService), null, roomMenu));
        maintenanceMenuItemsList.add(new MenuItem("Услуги гостя", new PrintMaintenancesOfGuestAction(guestService, roomService, maintenanceService), maintenancesOfGuestSortingMenu, rootMenu));
        maintenanceOfGuestSortingMenuItemList.add(new MenuItem("Без сортировки", new PrintMaintenancesOfGuestSortedNot(guestService, roomService, maintenanceService), null, maintenanceMenu));
        maintenanceOfGuestSortingMenuItemList.add(new MenuItem("Сортировать по цене", new PrintMaintenancesOfGuestSortedByPrice(guestService, roomService, maintenanceService), null,maintenanceMenu));
        maintenanceOfGuestSortingMenuItemList.add(new MenuItem("Сортировать по времени", new PrintMaintenancesOfGuestSortedByTime(guestService, roomService, maintenanceService), null, maintenanceMenu));
        maintenanceMenuItemsList.add(new MenuItem("Полная информация об услуге", new PrintMaintenanceByIdAction(guestService, roomService, maintenanceService),null, rootMenu));
    }

    Menu getRootMenu() {
        return rootMenu;
    }
}
