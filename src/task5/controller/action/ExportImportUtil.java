package task5.controller.action;

import task5.service.GuestService;
import task5.service.MaintenanceService;
import task5.service.RoomService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ExportImportUtil {

    public static List<List<String>> readDataFile(File csvFile) {
        List<List<String>> records = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(new LinkedList<>(Arrays.asList(values)));
                try {
                    long test = Long.parseLong(records.get(i).get(0)); //Если первый столбец не начинается с id
                } catch (NumberFormatException e) {
                    records.remove(i);
                    --i; //not to iterate further cause we've just removed an item
                }
                ++i;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public static List<String> readGuestData(GuestService guestService) {
        List<Long> guestIdList = new ArrayList<>();
        long guestId;

        System.out.println("Введите id гостей, чьи данные вы хотите экспортировать (1 Enter = 1 id), 0 - конец ввода.");
        do {
            guestId = ConsoleReaderUtil.inputGuestId();
            if (guestId != 0) guestIdList.add(guestId);
        } while (guestId != 0);

        List<String> export = new ArrayList<>();
        export.add("id,Name,Passport,CheckInDate [dd.MM.yyyy],CheckOutDate [dd.MM.yyyy],roomId");
        guestIdList.forEach(id -> {
            try {
                export.add(guestService.convertDataToExportFormat(id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return export;
    }

    public static List<String> readRoomData(RoomService roomService) {
        List<Long> roomIdList = new ArrayList<>();
        long roomId;

        System.out.println("Введите id комнат, чьи данные вы хотите экспортировать (1 Enter = 1 id), 0 - конец ввода.");
        do {
            roomId = ConsoleReaderUtil.inputRoomId();
            if (roomId != 0) roomIdList.add(roomId);
        } while (roomId != 0);

        List<String> export = new ArrayList<>();
        export.add("id,Name,Capacity,Stars,Status,Price");
        roomIdList.forEach(id -> {
            try {
                export.add(roomService.convertDataToExportFormat(id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return export;
    }

    public static List<String> readMaintenanceData(MaintenanceService maintenanceService) {
        List<Long> maintenanceIdList = new ArrayList<>();
        long maintenanceId;

        System.out.println("Введите id услуг, чьи данные вы хотите экспортировать (1 Enter = 1 id), 0 - конец ввода.");
        do {
            maintenanceId = ConsoleReaderUtil.inputMaintenanceId();
            if (maintenanceId != 0) maintenanceIdList.add(maintenanceId);
        } while (maintenanceId != 0);

        List<String> export = new ArrayList<>();
        export.add("id,Name,Price,Category");
        maintenanceIdList.forEach(id -> {
            try {
                export.add(maintenanceService.convertDataToExportFormat(id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return export;
    }
}
