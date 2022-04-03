package task5.controller.action.guest;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class PrintGuestPaymentAction extends AbstractAction implements IAction {
    public PrintGuestPaymentAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int guestId;

        try {
            guestId = inputGuestId();
            System.out.println(guestService.getGuestById(guestId).getFullName()
                    + "; К оплате: "
                    + guestService.getGuestById(guestId).getPayment());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}