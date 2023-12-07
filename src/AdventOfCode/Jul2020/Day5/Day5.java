package AdventOfCode.Jul2020.Day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 {
    public static void main(String[] args) {
        try {
            File file = new File("src\\AdventOfCode\\Day5\\Input");
            Scanner sc = new Scanner(file);

            List<Integer> seats = IntStream.rangeClosed(0, 127 * 8 + 7).boxed().collect(Collectors.toList());
            int highest = 0;

            while (sc.hasNextLine()) {
                int seat = binarySearch(sc.nextLine());
                if (seat > highest) { highest = seat; }
                seats.remove((Integer) seat);
            }
            System.out.println("Part one: " + highest);
            System.out.println("Part two: " + findMySeat(seats));

        } catch (FileNotFoundException e) {
            System.out.println("Fuck Java");
        }
    }

    public static int binarySearch(String binarySeat) {
        int minRow = 0;
        int maxRow = 127;
        int minCol = 0;
        int maxCol = 7;
        for (char c : binarySeat.toCharArray()) {
            switch (c) {
                case 'F' -> maxRow = (minRow + maxRow) / 2;
                case 'B' -> minRow = (minRow + maxRow) / 2;
                case 'L' -> maxCol = (minCol + maxCol) / 2;
                case 'R' -> minCol = (minCol + maxCol) / 2;
            }
        }
        return maxRow * 8 + maxCol;
    }

    public static int findMySeat(List<Integer> seats) {
        return seats
                .stream()
                .filter(seat -> !(seats.contains(seat - 1) || seats.contains(seat + 1)))
                .findAny().orElse(0);
    }
}
