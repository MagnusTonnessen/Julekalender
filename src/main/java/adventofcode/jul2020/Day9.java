package adventofcode.jul2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day9 {

    static ArrayList<Integer> numList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            File file = new File("src\\Input.txt");
            Scanner sc = new Scanner(file);
            int index = 0;
            for (int i = 0; i < 25; i++) { numList.add(sc.nextInt()); }

            while (sc.hasNextLine()) {
                int num = sc.nextInt();
                if (valid(index++, num)) {
                    numList.add(num);
                } else {
                    System.out.println("Part one: " + num);
                    System.out.println("Part two: " + partTwo(num));
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fuck Java");
        }
    }

    static boolean valid(int startIndex, int n) {
        return numList.subList(startIndex, startIndex + 25).stream().anyMatch(i -> numList.subList(startIndex, startIndex + 25).contains(n - i));
    }

    static int partTwo(int n) {
        Set<Integer> numbers = new HashSet<>();
        loop: for (int i = 0; i < numList.size(); i++) {
            numbers.clear();
            for (int j = i; j < numList.size(); j++) {
                numbers.add(numList.get(j));
                int sum = numbers.stream().mapToInt(Integer::intValue).sum();
                if (sum > n) { break; }
                if (sum == n) { break loop; }
            }
        }
        return Collections.max(numbers) + Collections.min(numbers);
    }
}
