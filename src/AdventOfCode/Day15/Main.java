package AdventOfCode.Day15;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> input = Arrays.asList(2, 1, 10, 11, 0, 6);

        System.out.println("Part one: " + getNthNumber(input, 2020));
        System.out.println("Part two: " + getNthNumber(input, 30000000));
    }

    static int getNthNumber(List<Integer> input, int nth) {
        Map<Integer, List<Integer>> occurrences = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            int cpy = i;
            occurrences.put(input.get(i), new ArrayList<>() {{ add(cpy + 1); }});
        }

        int prev = input.get(input.size() - 1);

        for (int turn = input.size() + 1; turn <= nth; turn++) {
            List<Integer> occPrev = occurrences.getOrDefault(prev, new ArrayList<>());

            int next = occPrev.size() == 1 ? 0 : occPrev.get(occPrev.size() - 1) - occPrev.get(occPrev.size() - 2);

            occurrences.putIfAbsent(next, new ArrayList<>());
            occurrences.get(next).add(turn);
            prev = next;
        }
        return prev;
    }
}
