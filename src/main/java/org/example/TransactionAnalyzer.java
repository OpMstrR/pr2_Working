
package org.example;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class TransactionAnalyzer {

    // Перетворення рядка на дату
    private static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return LocalDate.parse(
                    date,
                    DateTimeFormatter.ofPattern("dd-MM-yyyy")
            );
        }
    }

    // Обчислення загального балансу
    public static double calculateTotalBalance(
            List<Transaction> transactions) {

        double balance = 0;

        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }

        return balance;
    }

    // Підрахунок транзакцій за конкретний місяць і рік
    public static int countTransactionsByMonth(
            List<Transaction> transactions,
            String monthYear) {

        int count = 0;

        for (Transaction transaction : transactions) {

            LocalDate date = parseDate(transaction.getDate());

            String transactionMonthYear =
                    date.format(DateTimeFormatter.ofPattern("MM-yyyy"));

            if (transactionMonthYear.equals(monthYear)) {
                count++;
            }
        }

        return count;
    }

    // Отримання 10 найбільших витрат
    public static List<Transaction> findTopExpenses(
            List<Transaction> transactions) {

        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    // Найбільша витрата за вказаний період
    public static Optional<Transaction> getLargestExpense(
            List<Transaction> transactions,
            String startDate,
            String endDate) {

        LocalDate start = parseDate(startDate);
        LocalDate end = parseDate(endDate);

        Transaction largest = null;

        for (Transaction transaction : transactions) {

            LocalDate date = parseDate(transaction.getDate());

            if (transaction.getAmount() < 0 &&
                    !date.isBefore(start) &&
                    !date.isAfter(end)) {

                if (largest == null ||
                        transaction.getAmount() < largest.getAmount()) {

                    largest = transaction;
                }
            }
        }

        return Optional.ofNullable(largest);
    }

    // Найменша витрата за вказаний період
    public static Optional<Transaction> getSmallestExpense(
            List<Transaction> transactions,
            String startDate,
            String endDate) {

        LocalDate start = parseDate(startDate);
        LocalDate end = parseDate(endDate);

        Transaction smallest = null;

        for (Transaction transaction : transactions) {

            LocalDate date = parseDate(transaction.getDate());

            if (transaction.getAmount() < 0 &&
                    !date.isBefore(start) &&
                    !date.isAfter(end)) {

                if (smallest == null ||
                        transaction.getAmount() > smallest.getAmount()) {

                    smallest = transaction;
                }
            }
        }

        return Optional.ofNullable(smallest);
    }

    // Створення текстового звіту
    public static String generateTextReport(
            List<Transaction> transactions) {

        Map<String, Double> categoryTotals = new TreeMap<>();
        Map<String, Double> monthTotals = new TreeMap<>();

        for (Transaction transaction : transactions) {

            if (transaction.getAmount() < 0) {

                double expense = Math.abs(transaction.getAmount());

                // Підсумовування витрат за категоріями
                String category = transaction.getCategory();

                categoryTotals.put(
                        category,
                        categoryTotals.getOrDefault(category, 0.0) + expense
                );

                // Підсумовування витрат за місяцями
                LocalDate date = parseDate(transaction.getDate());

                String month = date.format(
                        DateTimeFormatter.ofPattern("MM-yyyy")
                );

                monthTotals.put(
                        month,
                        monthTotals.getOrDefault(month, 0.0) + expense
                );
            }
        }

        StringBuilder report = new StringBuilder();

        report.append("========== ЗВІТ ПРО ВИТРАТИ ==========\n");
        report.append("Один символ * = 1000 грн\n\n");

        report.append("ВИТРАТИ ЗА КАТЕГОРІЯМИ:\n");

        for (String category : categoryTotals.keySet()) {

            double total = categoryTotals.get(category);
            int stars = (int) (total / 1000);

            report.append(category)
                    .append(": ")
                    .append(total)
                    .append(" грн ")
                    .append("*".repeat(stars))
                    .append("\n");
        }

        report.append("\nВИТРАТИ ЗА МІСЯЦЯМИ:\n");

        for (String month : monthTotals.keySet()) {

            double total = monthTotals.get(month);
            int stars = (int) (total / 1000);

            report.append(month)
                    .append(": ")
                    .append(total)
                    .append(" грн ")
                    .append("*".repeat(stars))
                    .append("\n");
        }

        report.append("\n=====================================");

        return report.toString();
    }
}
