package Knowit.Day5;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        try {
            char[][] grid = new char[3079][3059];
            for (int i = 0; i < 3079; i++) {
                for (int j = 0; j < 3059; j++) {
                    grid[i][j] = ' ';
                }
            }
            AtomicInteger x = new AtomicInteger(1529);
            AtomicInteger y = new AtomicInteger(1539);
            Files.readAllLines(Path.of("src\\Knowit\\Day5\\Input")).get(0).chars().mapToObj(c -> (char) c).forEach(c -> {
                switch (c) {
                    case 'O' -> grid[y.getAndDecrement()][x.get()] = '.';
                    case 'N' -> grid[y.getAndIncrement()][x.get()] = '.';
                    case 'H' -> grid[y.get()][x.getAndDecrement()] = '.';
                    case 'V' -> grid[y.get()][x.getAndIncrement()] = '.';
                }
            });

            for (int i = 0; i < 3079; i++) {
                boolean insideRoom = false;
                for (int j = 0; j < 3059; j++) {
                    if (grid[i][j] == '.') {
                        if (grid[i][j+1] == ' ') {
                            insideRoom = !insideRoom;
                        }
                    }
                    if (insideRoom) {
                        grid[i][j] = '.';
                    }
                }
            }
            for (char[] cArr :grid) {
                if (new String(cArr).contains(".")) {
                    System.out.println(new String(cArr));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
