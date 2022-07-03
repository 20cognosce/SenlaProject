package com.senla.javacourse.controller.action;

import com.senla.javacourse.service.AbstractService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ImportExportUtil {

    public static List<List<String>> readDataFile(File csvFile) {
        List<List<String>> records = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(new LinkedList<>(Arrays.asList(values)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public static <T extends AbstractService<?, ?>> List<String> getDataForExport(T service) {
        List<Long> idList = new ArrayList<>();
        long entityId;

        System.out.println("Введите id объектов, чьи данные вы хотите экспортировать (1 Enter = 1 id, 0 - конец ввода): ");
        do {
            entityId = ConsoleReaderUtil.inputId("Введите идентификатор объекта: ");
            if (entityId != 0) idList.add(entityId);
        } while (entityId != 0);

        List<String> export = new ArrayList<>();
        idList.forEach(id -> {
            try {
                export.add(service.exportData(id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return export;
    }
}
