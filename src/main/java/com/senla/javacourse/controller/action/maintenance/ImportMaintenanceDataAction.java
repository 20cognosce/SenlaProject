package com.senla.javacourse.controller.action.maintenance;

import com.senla.javacourse.controller.action.AbstractAction;
import com.senla.javacourse.controller.action.ImportExportUtil;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.service.RoomService;

import java.io.File;
import java.util.List;

public class ImportMaintenanceDataAction extends AbstractAction {
    public ImportMaintenanceDataAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        File csvFile = new File("src/main/java/task5/resources/import_files/maintenance.csv");
        List<List<String>> records = ImportExportUtil.readDataFile(csvFile);
        getMaintenanceService().importData(records);
    }
}
