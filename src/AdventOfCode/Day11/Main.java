package AdventOfCode.Day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("src\\AdventOfCode\\Day11\\Input");
            Scanner sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Fuck Java");
        }
    }
}
