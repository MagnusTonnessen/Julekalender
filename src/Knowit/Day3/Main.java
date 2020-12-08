package Knowit.Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("src\\Knowit\\Day2\\Input");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {

            }
        } catch (FileNotFoundException e) {
            System.out.println("Fuck Java");
        }
    }
}
