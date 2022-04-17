package task5.controller.action.room;

import task5.controller.PropertiesUtil;
import task5.controller.action.AbstractAction;
import task5.controller.action.ConsoleReaderUtil;
import task5.dao.entity.RoomStatus;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import javax.naming.ServiceUnavailableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ChangeRoomStatusAction extends AbstractAction  {
    public ChangeRoomStatusAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        int roomStatusIndex = -1;
        long roomId;
        ArrayList<RoomStatus> roomStatuses;

        try {
            if ("no".equals(PropertiesUtil.property.getProperty("ChangeRoomStatusPossibility")))
                throw new ServiceUnavailableException("Опция недоступна");
        } catch (Exception e) {
            System.out.println(e.getClass().getCanonicalName() + ": " + e.getMessage());
            return;
        }

        try {
            roomId = ConsoleReaderUtil.inputId("Введите идентификатор комнаты: ");
            Scanner scanner = new Scanner(System.in);

            System.out.println("Выберите статус номера (индекс): ");
            roomStatuses = new ArrayList<>(Arrays.asList(RoomStatus.values()));
            int i = 1;
            while (i < roomStatuses.size()) {
                System.out.println(i + ". " + roomStatuses.get(i - 1));
                ++i;
            }
            while (roomStatusIndex < 1 || roomStatusIndex > roomStatuses.size()) {
                roomStatusIndex = scanner.nextInt();
            }

            getRoomService().setStatus(roomId, roomStatuses.get(roomStatusIndex - 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}