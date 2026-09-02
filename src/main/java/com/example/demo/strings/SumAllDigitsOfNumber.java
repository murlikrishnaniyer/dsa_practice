package com.example.demo.strings;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SumAllDigitsOfNumber {
    public static void main(String[] args) {
        int number=12345;
        IntSummaryStatistics collect = Stream.of(String.valueOf(number).split("")).collect(Collectors.summarizingInt(Integer::parseInt));
        System.out.println(collect);


        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        System.out.println(numbers.stream().mapToInt(Integer::intValue).sum());

    }
}
