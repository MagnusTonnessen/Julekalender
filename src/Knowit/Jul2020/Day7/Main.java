package Knowit.Jul2020.Day7;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    static char[][] trees;

    public static void main(String[] args) {
        try {
            trees =
                Files
                    .readAllLines(Path.of("src\\Knowit\\Day7\\Input"))
                    .stream()
                    .map(String::toCharArray)
                    .toArray(char[][]::new);

            /*
            int symmetric = 0;
            int col = 0;
            while (col < trees[0].length) {
                if (symmetric(col)) {
                    symmetric++;
                }
                col = right(col) + 3;
            }
            System.out.println(symmetric);*/
            System.out.println(symmetric(0));
        } catch (Exception e) {
            System.out.println("Fuck Java");
        }
    }

    static int right(int left) {
        int col;
        colLoop: for (col = left; col < trees[0].length; col++) {
            for (char[] row : trees) {
                if (row[col] == '#') {
                    continue colLoop;
                }
            }
            return col - 1;
        }
        return col - 1;
    }

    static boolean symmetric(int left) {
        int right = right(left);
        int width = (right - left) / 2;
        for (int i = 0; i < width; i++) {
            for (char[] row : trees) {
                if (row[left + i] != row[right - i]) {
                    return false;
                }
            }
        }
        return true;
    }
}
