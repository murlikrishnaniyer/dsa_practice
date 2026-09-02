package com.example.demo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

public class CalculateClosingBalance {

    // Define the Transaction record
    public record Transaction(String transactionId, String accountId, BigDecimal amount) {}

    /**
     * Requirement 1: Calculates closing balance per account (ignoring duplicates).
     */
    public static Map<String, BigDecimal> getClosingBalances(List<Transaction> transactions) {
        return transactions.stream()
            .collect(Collectors.groupingBy(
                Transaction::accountId,
                Collectors.mapping(
                    Transaction::amount,
                    Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                )
            ));
    }

    /**
     * Requirement 2: Deduplicates transactions by reference ID, then calculates balances.
     */
    public static Map<String, BigDecimal> getBalancesWithoutDuplicates(List<Transaction> transactions) {
        Set<String> seenIds = new HashSet<>();
        
        return transactions.stream()
            .filter(tx -> seenIds.add(tx.transactionId())) // Keeps only the first occurrence of a transactionId
            .collect(Collectors.groupingBy(
                Transaction::accountId,
                Collectors.mapping(
                    Transaction::amount,
                    Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                )
            ));
    }

    public static void main(String[] args) {
        // Sample data with mixed positive/negative amounts and duplicate transactionIds
        List<Transaction> transactions = List.of(
            new Transaction("TX101", "ACC_01", new BigDecimal("1000.00")),
            new Transaction("TX102", "ACC_01", new BigDecimal("-200.50")),
            new Transaction("TX103", "ACC_02", new BigDecimal("500.00")),
            new Transaction("TX101", "ACC_01", new BigDecimal("1000.00")), // Duplicate
            new Transaction("TX104", "ACC_02", new BigDecimal("-50.00")),
            new Transaction("TX103", "ACC_02", new BigDecimal("500.00"))  // Duplicate
        );

        System.out.println("--- Raw Closing Balances (Includes Duplicates) ---");
        Map<String, BigDecimal> rawBalances = getClosingBalances(transactions);
        rawBalances.forEach((acc, bal) -> System.out.println(acc + " -> $" + bal));

        System.out.println("\n--- Deduplicated Closing Balances ---");
        Map<String, BigDecimal> cleanBalances = getBalancesWithoutDuplicates(transactions);
        cleanBalances.forEach((acc, bal) -> System.out.println(acc + " -> $" + bal));
    }
}
