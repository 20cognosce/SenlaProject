package task5.controller.action.maintenance;

import task5.controller.action.AbstractAction;
import task5.controller.action.ExportImportUtil;
import task5.dao.model.MaintenanceCategory;
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

        List<List<String>> records = ExportImportUtil.readDataFile(csvFile);
        records.forEach(entry -> {
            try {
                long maintenanceId = Long.parseLong(entry.get(0));
                String name = entry.get(1);
                int price = Integer.parseInt(entry.get(2));
                MaintenanceCategory maintenanceCategory = MaintenanceCategory.valueOf(entry.get(3));

                getMaintenanceService().createMaintenance(maintenanceId, name, price, maintenanceCategory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
