package adventofcode.jul2020;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day14 {
    public static void main(String[] args) {
        try {
            File file = new File("src\\Input.txt");
            Scanner sc = new Scanner(file);
            Map<Long, Long> memoryPartOne = new HashMap<>();
            Map<Long, Long> memoryPartTwo = new HashMap<>();
            String mask = "";
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.startsWith("mask")) {
                    mask = line.split(" = ")[1];
                } else {
                    String[] op = line.replace("mem[", "").split("] = ");
                    long loc = Long.parseLong(op[0]);
                    long num = Long.parseLong(op[1]);
                    memoryPartOne.put(loc, applyMaskPartOne(mask, num));
                    applyMaskPartTwo(mask, loc).forEach(l -> memoryPartTwo.put(l, num));
                }
            }

            System.out.println("Part one: " + memoryPartOne.values().stream().reduce(0L, Long::sum));
            System.out.println("Part two: " + memoryPartTwo.values().stream().reduce(0L, Long::sum));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fuck Java");
        }
    }

    static long applyMaskPartOne(String mask, long num) {
        String value = String.format("%36s", Long.toBinaryString(num)).replace(" ", "0");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < mask.length(); i++) {
            result.append(mask.charAt(i) == 'X' ? value.charAt(i) : mask.charAt(i));
        }
        return Long.parseLong(result.toString(), 2);
    }

    static Set<Long> applyMaskPartTwo(String mask, long num) {
        String value = String.format("%36s", Long.toBinaryString(num)).replace(" ", "0");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < mask.length(); i++) {
            result.append(mask.charAt(i) != '0' ? mask.charAt(i) : value.charAt(i));
        }
        Set<Long> results = new HashSet<>();
        permutations(result.toString(), results);
        return results;
    }

    static void permutations(String result, Set<Long> results) {
        int i = result.indexOf('X');
        if (i == -1) {
            results.add(Long.parseLong(result, 2));
        } else {
            char[] resultArr = result.toCharArray();
            resultArr[i] = '1';
            permutations(new String(resultArr), results);
            resultArr[i] = '0';
            permutations(new String(resultArr), results);
        }
    }
}