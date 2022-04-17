package task5.controller.action.room;

import task5.controller.action.AbstractAction;
import task5.controller.action.ImportExportUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.io.File;

public class ExportRoomDataAction extends AbstractAction {
    public ExportRoomDataAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        File csvFile = new File("src/main/java/task5/resources/export_files/room.csv");
        getRoomService().exportRecordsToFile(ImportExportUtil.getDataForExport(getRoomService()), csvFile);
    }
}
