package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TransactionAnalyzerTest {

    // Тест визначення 10 найбільших витрат
    @Test
    public void testTop10Expenses() {

        List<Transaction> transactions = new ArrayList<>();

        // Створення 12 тестових витрат
        for (int i = 1; i <= 12; i++) {
            transactions.add(
                    new Transaction(
                            "2023-01-01",
                            -i * 1000.0,
                            "Expense " + i,
                            "Food"
                    )
            );
        }

        // Додавання доходу, який не повинен потрапити до витрат
        transactions.add(
                new Transaction("2023-01-01", 50000.0, "Salary", "Income")
        );

        // Отримання ТОП-10 витрат
        List<Transaction> top10 =
                TransactionAnalyzer.findTopExpenses(transactions);

        // Перевірка кількості результатів
        Assertions.assertEquals(10, top10.size());

        // Найбільша витрата повинна бути -12000
        Assertions.assertEquals(-12000.0, top10.get(0).getAmount(), 0.001);

        // Десята витрата повинна бути -3000
        Assertions.assertEquals(-3000.0, top10.get(9).getAmount(), 0.001);
    }
}