package AdventOfCode.Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("src\\AdventOfCode\\Day2\\Input");
            Scanner sc = new Scanner(file);
            int validPassPartOne = 0;
            int validPassPartTwo = 0;
            while (sc.hasNextLine()) {
                String[] lim = sc.next().split("-");
                int min = Integer.parseInt(lim[0]);
                int max = Integer.parseInt(lim[1]);
                char c = sc.next().charAt(0);
                String password = sc.next();
                validPassPartOne += partOne(min, max, c,password) ? 1 : 0;
                validPassPartTwo += partTwo(min, max, c, password) ? 1 : 0;
            }

            System.out.println("Part one: " + validPassPartOne);
            System.out.println("Part two: " + validPassPartTwo);
        } catch (FileNotFoundException e) {
            System.out.println("Fuck Java");
        }
    }

    public static boolean partOne(int min, int max, char ch, String pass) {
        long count = pass.chars().mapToObj(c -> (char) c).filter(c -> c == ch).count();
        return count >= min && count <= max;
    }

    public static boolean partTwo(int min, int max, char c, String pass) {
        return (pass.charAt(min - 1) == c) ^ (pass.charAt(max - 1) == c);
    }
}
