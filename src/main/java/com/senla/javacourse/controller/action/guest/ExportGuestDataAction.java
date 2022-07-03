package com.senla.javacourse.controller.action.guest;

import com.senla.javacourse.controller.action.AbstractAction;
import com.senla.javacourse.controller.action.ImportExportUtil;
import com.senla.javacourse.service.GuestService;
import com.senla.javacourse.service.MaintenanceService;
import com.senla.javacourse.service.RoomService;

import java.io.File;

public class ExportGuestDataAction extends AbstractAction {
    public ExportGuestDataAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        File csvFile = new File("src/main/java/task5/resources/export_files/guest.csv");
        getGuestService().exportRecordsToFile(ImportExportUtil.getDataForExport(getGuestService()), csvFile);
    }
}