package task5.controller.action.room;

import task5.controller.action.AbstractAction;
import task5.controller.action.ExportImportUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.io.File;
import java.util.List;

public class ImportRoomDataAction extends AbstractAction {
    public ImportRoomDataAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        File csvFile = new File("src/task5/import_files/room.csv");
        List<List<String>> records = ExportImportUtil.readDataFile(csvFile);
        getRoomService().importRoomRecords(records);
    }
}
