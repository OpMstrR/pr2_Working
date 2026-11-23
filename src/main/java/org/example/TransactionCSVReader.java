
package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {

    // Читання даних із CSV
    public static List<String> readData(String source) {

        List<String> data = new ArrayList<>();

        try {
            BufferedReader br;

            if (source.startsWith("http://") ||
                    source.startsWith("https://")) {

                URL url = new URL(source);

                br = new BufferedReader(
                        new InputStreamReader(
                                url.openStream(),
                                StandardCharsets.UTF_8
                        )
                );

            } else {

                br = Files.newBufferedReader(
                        Path.of(source),
                        StandardCharsets.UTF_8
                );
            }

            try (br) {
                String line;

                while ((line = br.readLine()) != null) {
                    data.add(line);
                }
            }

        } catch (IOException e) {
            System.out.println(
                    "Помилка читання CSV: " + e.getMessage()
            );
        }

        return data;
    }

    // Обробка отриманих даних
    public static List<Transaction> processData(List<String> data) {

        List<Transaction> transactions = new ArrayList<>();

        for (String line : data) {

            String[] values = line.split(",", 4);

            if (values.length >= 3) {

                try {
                    double amount =
                            Double.parseDouble(values[1].trim());

                    String category = "Без категорії";

                    if (values.length == 4 &&
                            !values[3].trim().isEmpty()) {

                        category = values[3].trim();
                    }

                    Transaction transaction = new Transaction(
                            values[0].trim(),
                            amount,
                            values[2].trim(),
                            category
                    );

                    transactions.add(transaction);

                } catch (NumberFormatException e) {

                    System.out.println(
                            "Помилка обробки рядка: " + line
                    );
                }
            }
        }

        return transactions;
    }
}
