package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class TransactionCSVReaderTest {

    @TempDir
    Path tempDir;

    // Тест читання CSV testss
    @Test
    public void testReadCSV() throws Exception {

        Path file = tempDir.resolve("transactions.csv");

        Files.writeString(file,
                "2023-01-01,1000,Salary,Income\n" +
                        "2023-01-02,-500,Food,Food\n" +
                        "2023-01-03,-200,Transport,Transport\n");

        // Читання даних із CSV
        List<String> data =
                TransactionCSVReader.readData(file.toString());

        Assertions.assertEquals(3, data.size());

        // Обробка отриманих даних
        List<Transaction> transactions =
                TransactionCSVReader.processData(data);

        Assertions.assertEquals(3, transactions.size());
        Assertions.assertEquals("Food", transactions.get(1).getCategory());
        Assertions.assertEquals(-500.0, transactions.get(1).getAmount(), 0.001);
    }
}