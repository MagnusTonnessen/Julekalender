package Third;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("src\\Third\\Input");
            int[] right = new int[] {1, 3, 5, 7, 1};
            int[] down = new int[] {1, 1, 1, 1, 2};
            int[] treeCount = new int[5];
            for (int i = 0; i < right.length; i++) {
                Scanner sc = new Scanner(file);
                int x = 0;
                sc.nextLine();
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (down[i] == 2) { line = sc.nextLine(); }
                    x = (x + right[i]) % line.length();
                    if (line.charAt(x) == '#') { treeCount[i]++; }
                }
            }
            System.out.println("Part one: " + treeCount[1]);
            System.out.println("Part two: " + IntStream.of(treeCount).reduce(1, (a, b) -> a * b));
        } catch (FileNotFoundException e) {
            System.out.println("Fuck Java");
        }
    }
}
