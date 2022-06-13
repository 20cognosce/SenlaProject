package javacourse.task5.controller.action.guest;

import javacourse.task5.controller.action.AbstractAction;
import javacourse.task5.controller.action.ConsoleReaderUtil;
import javacourse.task5.service.GuestService;
import javacourse.task5.service.MaintenanceService;
import javacourse.task5.service.RoomService;

public class PrintGuestPaymentAction extends AbstractAction  {
    public PrintGuestPaymentAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long guestId = ConsoleReaderUtil.inputId("Введите идентификатор гостя: ");
        System.out.println("id: " + guestId + "; К оплате: " + getGuestService().getPriceByGuest(guestId));
    }
}