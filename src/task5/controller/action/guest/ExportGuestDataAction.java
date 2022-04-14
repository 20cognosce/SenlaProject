package task5.controller.action.guest;

import task5.controller.action.AbstractAction;
import task5.controller.action.ImportExportUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.io.File;

public class ExportGuestDataAction extends AbstractAction {
    public ExportGuestDataAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        File csvFile = new File("src/task5/export_files/guest.csv");
        getGuestService().exportRecordsToFile(ImportExportUtil.getExportedData(getGuestService()), csvFile);
    }
}