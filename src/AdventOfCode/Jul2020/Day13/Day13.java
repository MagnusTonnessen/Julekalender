package AdventOfCode.Jul2020.Day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.util.stream.Collectors.toMap;

public class Day13 {
    public static void main(String[] args) {
        try {
            File file = new File("src\\AdventOfCode\\Day13\\Input");
            Scanner sc = new Scanner(file);

            long arrival = Integer.parseInt(sc.nextLine());
            String[] input = sc.nextLine().split(",");

            Arrays.stream(input)
                    .filter(i -> !i.equals("x"))
                    .map(Integer::parseInt)
                    .collect(toMap(i -> i, i -> i - arrival % i))
                    .entrySet()
                    .stream()
                    .min(Comparator.comparingLong(Map.Entry::getValue))
                    .ifPresent(e -> System.out.println("Part one: " + (e.getKey() * e.getValue())));

            System.out.println("Part two: " + CRT(
                Arrays.stream(input)
                    .filter(i -> !i.equals("x"))
                    .map(Integer::parseInt)
                    .collect(toMap(i -> i, i -> (i - Arrays.asList(input).indexOf(String.valueOf(i))) % i))
                    .entrySet()
                    .stream()
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue))
            ));
        } catch (FileNotFoundException e) {
            System.out.println("Fuck Java");
        }
    }

    static long CRT(Map<Integer, Integer> numRem) {
        List<Integer> num = new ArrayList<>(numRem.keySet());
        List<Integer> rem = new ArrayList<>(numRem.values());

        int k = num.size();

        long prod = num.stream().map(Integer::toUnsignedLong).reduce(1L, (a, b) -> a * b);

        long result = 0;

        for (int i = 0; i < k; i++) {
            long pp = prod / num.get(i);
            result += (long) rem.get(i) * inverse(pp, num.get(i)) * pp;
        }

        return result % prod;
    }

    static long inverse(long a, long m) {
        long m0 = m, t, q;
        long x0 = 0, x1 = 1;

        if (m == 1) { return 0; }

        while (a > 1) {
            q = a / m;
            t = m;
            m = a % m;
            a = t;
            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }

        if (x1 < 0) { x1 += m0; }

        return x1;
    }
}
