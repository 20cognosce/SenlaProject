package task5.controller.action.room;

import task5.controller.action.AbstractAction;
import task5.controller.action.ExportImportUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ExportRoomDataAction extends AbstractAction {
    public ExportRoomDataAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        File csvFile = new File("src/task5/export_files/room.csv");
        PrintWriter printWriter;

        try {
            printWriter = new PrintWriter(csvFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        ExportImportUtil.readRoomData(getRoomService()).forEach(printWriter::println);
        printWriter.close();
    }
}
