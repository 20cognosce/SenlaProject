package javacourse.task5.service;

import javacourse.task5.dao.AbstractDao;
import javacourse.task5.dao.entity.AbstractEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.NoSuchElementException;

public interface AbstractService<T extends AbstractEntity, D extends AbstractDao<T>> {
    void addAll(List<T> list);
    T getById(long id);

    String getExportTitleLine();
    String exportData(long id) throws NoSuchElementException, ClassNotFoundException;
    void importData(List<List<String>> records);
    default void exportRecordsToFile(List<String> records, File csvFile) {
        PrintWriter printWriter;

        try {
            printWriter = new PrintWriter(csvFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        printWriter.println(getExportTitleLine());
        records.forEach(printWriter::println);
        printWriter.close();
    }
}
