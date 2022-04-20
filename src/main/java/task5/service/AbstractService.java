package task5.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.NoSuchElementException;

public interface AbstractService<T> {
    List<T> getAll();
    void addAll(List<T> list);
    T getByName(String name) throws NoSuchElementException;
    T getById(long id) throws NoSuchElementException;

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
