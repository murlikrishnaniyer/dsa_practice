package com.example.demo;



import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

public class TransactionProcessor {


    /*
      “Given a list of account transactions (each with an account ID and a signed amount),
       write a method that returns the closing balance per account.
       Then handle duplicate transactions identified by a transaction reference.”

     */
    // 1. Define the Data Models using Records
    public record Transaction(String transactionId, String accountId, double amount) {}
    public record AccountBalance(String accountId, double balance) {}

    /**
     * Requirement 1: Calculate closing balance per account.
     */
    public static Map<String, Double> getClosingBalances(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::accountId,
                        Collectors.summingDouble(Transaction::amount)
                ));
    }

    /**
     * Requirement 2: Calculate closing balance while ignoring duplicate transaction IDs.
     */
    public static Map<String, Double> getBalancesWithoutDuplicates(List<Transaction> transactions) {
        Set<String> seenTransactions = new HashSet<>();
        
        return transactions.stream()
                // Filter out transactions if their ID has already been seen
                .filter(tx -> seenTransactions.add(tx.transactionId())) 
                .collect(Collectors.groupingBy(
                        Transaction::accountId,
                        Collectors.summingDouble(Transaction::amount)
                ));
    }

    public static void main(String[] args) {
        // Sample data containing duplicate transaction IDs (TX102 and TX104)
        List<Transaction> transactions = List.of(
                new Transaction("TX101", "ACC_A", 1000.00),
                new Transaction("TX102", "ACC_B", 500.00),
                new Transaction("TX102", "ACC_B", 500.00),  // Duplicate ID & values
                new Transaction("TX103", "ACC_A", -200.50), // Negative amount (withdrawal)
                new Transaction("TX104", "ACC_C", 300.00),
                new Transaction("TX104", "ACC_C", -150.00), // Duplicate ID but different data
                new Transaction("TX105", "ACC_B", -100.00)
        );

        System.out.println("--- 1. Closing Balances (Including Duplicates) ---");
        Map<String, Double> basicBalances = getClosingBalances(transactions);
        basicBalances.forEach((accId, bal) -> System.out.printf("Account: %s | Balance: $%.2f%n", accId, bal));

        System.out.println("\n--- 2. Closing Balances (Deduplicated by Transaction ID) ---");
        Map<String, Double> cleanBalances = getBalancesWithoutDuplicates(transactions);
        cleanBalances.forEach((accId, bal) -> System.out.printf("Account: %s | Balance: $%.2f%n", accId, bal));
    }
}
