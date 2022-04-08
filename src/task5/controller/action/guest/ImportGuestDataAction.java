package task5.controller.action.guest;

import task5.controller.action.AbstractAction;
import task5.controller.action.ExportImportUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ImportGuestDataAction extends AbstractAction {
    public ImportGuestDataAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        File csvFile = new File("src/task5/import_files/guest.csv");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        List<List<String>> records = ExportImportUtil.readDataFile(csvFile);
        records.forEach(entry -> {
            try {
                long guestId = Long.parseLong(entry.get(0));
                String name = entry.get(1);
                String passport = entry.get(2);
                LocalDate checkInDate = LocalDate.parse(entry.get(3), dtf);
                LocalDate checkOutDate = LocalDate.parse(entry.get(4), dtf);
                long roomId = Long.parseLong(entry.get(5));

                //sent roomId is 0 in order to check the room's ability to settle guests (status)
                getGuestService().createGuest(guestId, name, passport, checkInDate, checkOutDate, 0);
                if (roomId != 0) getGuestService().addGuestToRoom(guestId, roomId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
