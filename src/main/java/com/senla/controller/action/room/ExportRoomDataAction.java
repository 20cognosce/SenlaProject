package com.senla.controller.action.room;

import com.senla.controller.action.AbstractAction;
import com.senla.controller.action.ImportExportUtil;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import com.senla.service.RoomService;

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
