package Knowit.Day3;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        try {
            List<String> words = Files.readAllLines(Path.of("src\\Knowit\\Day3\\Input"));
            File file = new File("src\\Knowit\\Day3\\Matrix");
            Scanner sc = new Scanner(file);
            char[][] matrix = new char[1000][1000];
            int row = 0;

            while (sc.hasNextLine()) { matrix[row++] = sc.nextLine().toCharArray(); }

            System.out.println(words.stream().filter(word -> wordMissing(matrix, word)).sorted().collect(Collectors.joining(",")));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fuck Java");
        }
    }

    static boolean wordMissing(char[][] matrix, String word) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (search(matrix, row, col, word)) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean search(char[][] matrix, int row, int col, String word) {

        if (matrix[row][col] != word.charAt(0)) { return false; }

        int[] x = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] y = { -1, 0, 1, -1, 1, -1, 0, 1 };

        for (int dir = 0; dir < 8; dir++) {

            int k;
            int rd = row + x[dir];
            int cd = col + y[dir];

            for (k = 1; k < word.length(); k++) {

                if (rd >= matrix.length ||
                    rd < 0 ||
                    cd >= matrix[0].length ||
                    cd < 0 ||
                    matrix[rd][cd] != word.charAt(k)) { break; }

                rd += x[dir];
                cd += y[dir];
            }

            if (k == word.length()) { return true; }
        }
        return false;
    }
}
