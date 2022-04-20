package task5.controller.action.maintenance;

import task5.controller.action.AbstractAction;
import task5.controller.action.ImportExportUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.io.File;

public class ExportMaintenanceDataAction extends AbstractAction {
    public ExportMaintenanceDataAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        File csvFile = new File("src/task5/export_files/maintenance.csv");
        getMaintenanceService().exportRecordsToFile(ImportExportUtil.getDataForExport(getMaintenanceService()), csvFile);
    }
}
