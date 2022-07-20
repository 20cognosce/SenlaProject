package com.senla.controller.action.room;

import com.senla.controller.action.AbstractAction;
import com.senla.controller.action.ImportExportUtil;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import com.senla.service.RoomService;

import java.io.File;
import java.util.List;

public class ImportRoomDataAction extends AbstractAction {
    public ImportRoomDataAction(GuestService guestService, RoomService roomService, MaintenanceService maintenanceService) {
        super(guestService, roomService, maintenanceService);
    }

    @Override
    public void execute() {
        File csvFile = new File("src/main/java/task5/resources/import_files/room.csv");
        List<List<String>> records = ImportExportUtil.readDataFile(csvFile);
        getRoomService().importData(records);
    }
}