package com.example.demo.arrays;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;import java.util.stream.Collectors;

public class SingleNumber {
    public static void main(String[] args) {
        int[] arr = {4, 1, 2, 1, 2};

        // Approach 1: XOR using streams (works when every other element appears exactly twice)
        int singleByXor = Arrays.stream(arr).reduce(0, (a, b) -> a ^ b);
        System.out.println("Single (XOR): " + singleByXor);

        // Approach 2: Using streams + grouping to find element with frequency 1 (general and safe)
        Map<Integer, Long> freq = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        freq.entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .ifPresent(v -> System.out.println("Single (grouping): " + v));
    }
}
