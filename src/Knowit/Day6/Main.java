package Knowit.Day6;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("src\\Knowit\\Day6\\Input");
            Scanner sc = new Scanner(file);
            String[] input = sc.nextLine().split(",");
            long numberOfBites = Arrays.stream(input).mapToLong(Long::parseLong).sum();
            double numberOfPackages = 10000;
            double numberOfElves = 127;

            System.out.println(numberOfBites);
            System.out.println(numberOfBites / numberOfElves);
        } catch (Exception e) {
            System.out.println("Fuck Java");
        }
    }
}
