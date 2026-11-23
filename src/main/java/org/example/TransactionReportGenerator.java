
package org.example;

public abstract class TransactionReportGenerator {

    // Виведення звіту про баланс
    public static void printBalanceReport(double totalBalance) {

        System.out.println("Загальний баланс: " + totalBalance);

    }

    // Виведення кількості транзакцій за місяць
    public static void printTransactionsCountByMonth(
            String monthYear, int count) {

        System.out.println(
                "Кількість транзакцій за " +
                        monthYear + ": " + count
        );

    }
}
