package task5.controller.action.room;

import task5.controller.IAction;
import task5.controller.action.AbstractAction;
import task5.dao.model.Room;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

public class PrintRoomByIdAction extends AbstractAction implements IAction {
    public PrintRoomByIdAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int roomId;
        try {
            roomId = inputRoomId();

            Room temp = roomService.getRoomById(roomId);
            System.out.println(roomService.getRoomById(roomId).toString() + roomService.getGuestsListAsString(roomId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}