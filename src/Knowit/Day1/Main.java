package Knowit.Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            File file = new File("src\\Knowit\\Day1\\Input");
            Scanner sc = new Scanner(file);
            System.out.println("Missing number: " +
                ((100000L * (100000 + 1)) / 2 -
                (Arrays.stream(sc.nextLine().split(",")).mapToLong(Long::parseLong).sum()))
            );
        } catch (FileNotFoundException e) {
            System.out.println("Fuck Java");
        }
    }
}