package com.senla.javacourse.controller.action.maintenance;

import com.senla.javacourse.controller.action.AbstractAction;
import com.senla.javacourse.controller.action.ImportExportUtil;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.service.RoomService;

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
