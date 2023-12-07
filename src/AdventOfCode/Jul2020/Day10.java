package AdventOfCode.Jul2020;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10 {
    public static void main(String[] args) {
        try {
            List<Integer> numbers =
                    Files
                        .readAllLines(Path.of("src\\Input"))
                        .stream()
                        .mapToInt(Integer::parseInt)
                        .boxed()
                        .sorted()
                        .collect(Collectors.toList());

            numbers.add(0, 0);
            numbers.add(numbers.get(numbers.size() - 1) + 3);

            System.out.println("Part one: " + partOne(numbers));
            System.out.println("Part two: " + partTwo(numbers));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fuck Java");
        }
    }

    static long partOne(List<Integer> numbers) {
        Map<Integer, Long> count =
                IntStream
                    .range(0, numbers.size() - 1)
                    .boxed()
                    .map(i -> numbers.get(i + 1) - numbers.get(i))
                    .collect(Collectors.groupingBy(i -> i, Collectors.counting()));
        return count.get(1) * count.get(3);
    }

    static Long partTwo(List<Integer> numbers) {
        Map<Integer, Long> options = numbers.stream().collect(Collectors.toMap(i -> i, i -> Integer.toUnsignedLong(0)));
        options.put(0, 1L);
        for (int number :numbers) {
            for (int i = 1; i <= 3; i++) {
                if (options.containsKey(number - i)) {
                    options.put(number, options.get(number) + options.get(number - i));
                }
            }
        }
        return options.get(Collections.max(numbers));
    }
}
