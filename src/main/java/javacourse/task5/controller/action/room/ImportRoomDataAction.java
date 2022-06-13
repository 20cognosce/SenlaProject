package javacourse.task5.controller.action.room;

import javacourse.task5.controller.action.AbstractAction;
import javacourse.task5.controller.action.ImportExportUtil;
import javacourse.task5.service.GuestService;
import javacourse.task5.service.MaintenanceService;
import javacourse.task5.service.RoomService;

import java.io.File;
import java.util.List;

public class ImportRoomDataAction extends AbstractAction {
    public ImportRoomDataAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        File csvFile = new File("src/main/java/task5/resources/import_files/room.csv");
        List<List<String>> records = ImportExportUtil.readDataFile(csvFile);
        getRoomService().importData(records);
    }
}
