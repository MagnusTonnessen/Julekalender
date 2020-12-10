package AdventOfCode.Day10;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        try {
            List<Integer> numbers =
                    Files
                        .readAllLines(Path.of("src\\AdventOfCode\\Day10\\Input"))
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

    static long partTwo(List<Integer> numbers) {
        Set<List<Integer>> permutations = new HashSet<>() {{ add(numbers); }};
        long startTime = System.currentTimeMillis();
        remove(permutations, numbers);
        System.out.println("Execution time: " + ((System.currentTimeMillis() - startTime)/1000) + " seconds");
        return permutations.size();
    }

    static void remove(Set<List<Integer>> permutations, List<Integer> numbers) {
        for (int i = 1; i < numbers.size() - 1; i++) {
            if (numbers.size() == 96) {
                System.out.println(i + "/" + (numbers.size() - 2));
            }
            if (canRemove(numbers, i)) {
                int x = numbers.remove(i);
                if (!permutations.contains(numbers)) {
                    permutations.add(new ArrayList<>(numbers));
                }
                remove(permutations, numbers);
                numbers.add(i, x);
            }
        }
    }

    static boolean canRemove(List<Integer> numbers, int index) {
        return numbers.get(index + 1) - numbers.get(index - 1) < 4;
    }
}
