package task5.controller.action.guest;

import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class PrintGuestPaymentAction extends AbstractAction  {
    public PrintGuestPaymentAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        long guestId = ConsoleReaderUtil.inputId();
        System.out.println("id: " + guestId + "; К оплате: " + getGuestService().getPriceByGuest(guestId));
    }
}