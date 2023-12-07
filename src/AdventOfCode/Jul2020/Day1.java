package AdventOfCode.Jul2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day1 {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        try {
            File file = new File("src\\Input");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                numbers.add(sc.nextInt());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fuck Java");
        }

        System.out.println("Part one: " +
            numbers
                .stream()
                .filter(i -> numbers.contains(2020 - i))
                .reduce(1, (a, b) -> a * b)
        );

        System.out.println("Part two: " +
            numbers
                .stream()
                .filter(i -> numbers
                                .stream()
                                .anyMatch(j -> numbers.contains(2020 - i - j)))
            .reduce(1, (a, b) -> a * b)
        );
    }
}