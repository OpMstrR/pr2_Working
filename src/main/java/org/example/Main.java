
package org.example;

import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        String filePath = "https://informer.com.ua/dut/java/pr2.csv";

        // Читання даних
        List<String> data =
                TransactionCSVReader.readData(filePath);

        // Обробка даних
        List<Transaction> transactions =
                TransactionCSVReader.processData(data);

        // Виведення транзакцій
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }

        // Обчислення загального балансу
        double totalBalance =
                TransactionAnalyzer.calculateTotalBalance(transactions);

        TransactionReportGenerator.printBalanceReport(totalBalance);

        // Кількість транзакцій за місяць
        String monthYear = "01-2024";

        int transactionsCount =
                TransactionAnalyzer.countTransactionsByMonth(
                        transactions, monthYear
                );

        TransactionReportGenerator.printTransactionsCountByMonth(
                monthYear, transactionsCount
        );

        // 10 найбільших витрат
        System.out.println("\n========== ТОП-10 ВИТРАТ ==========");

        List<Transaction> topExpenses =
                TransactionAnalyzer.findTopExpenses(transactions);

        for (Transaction expense : topExpenses) {
            System.out.println(
                    expense.getDescription() + ": " +
                            expense.getAmount() + " грн"
            );
        }

        // Найбільша та найменша витрати за період
        String startDate = "01-01-2024";
        String endDate = "31-12-2024";

        System.out.println("\n========== ВИТРАТИ ЗА ПЕРІОД ==========");

        Optional<Transaction> largest =
                TransactionAnalyzer.getLargestExpense(
                        transactions, startDate, endDate
                );

        Optional<Transaction> smallest =
                TransactionAnalyzer.getSmallestExpense(
                        transactions, startDate, endDate
                );

        if (largest.isPresent()) {
            System.out.println("Найбільша витрата: " + largest.get());
        } else {
            System.out.println("Витрат за період немає.");
        }

        if (smallest.isPresent()) {
            System.out.println("Найменша витрата: " + smallest.get());
        } else {
            System.out.println("Витрат за період немає.");
        }

        // Створення текстового звіту
        System.out.println(
                "\n" + TransactionAnalyzer.generateTextReport(transactions)
        );
    }
}
