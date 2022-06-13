package javacourse.task5.controller.action.maintenance;

import javacourse.task5.controller.action.AbstractAction;
import javacourse.task5.controller.action.ImportExportUtil;
import javacourse.task5.service.GuestService;
import javacourse.task5.service.MaintenanceService;
import javacourse.task5.service.RoomService;

import java.io.File;

public class ExportMaintenanceDataAction extends AbstractAction {
    public ExportMaintenanceDataAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        File csvFile = new File("src/main/java/task5/resources/export_files/maintenance.csv");
        getMaintenanceService().exportRecordsToFile(ImportExportUtil.getDataForExport(getMaintenanceService()), csvFile);
    }
}
