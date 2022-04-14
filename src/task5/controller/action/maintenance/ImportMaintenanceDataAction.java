package task5.controller.action.maintenance;

import task5.controller.action.AbstractAction;
import task5.controller.action.ImportExportUtil;
import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.io.File;
import java.util.List;

public class ImportMaintenanceDataAction extends AbstractAction {
    public ImportMaintenanceDataAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        File csvFile = new File("src/task5/import_files/maintenance.csv");
        List<List<String>> records = ImportExportUtil.readDataFile(csvFile);
        getMaintenanceService().importData(records);
    }
}
