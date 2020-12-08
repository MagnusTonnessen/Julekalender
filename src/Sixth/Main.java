package Sixth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("src\\Sixth\\Input");
            Scanner sc = new Scanner(file);

            HashMap<Integer, Integer> groupCount = new HashMap<>();
            groupCount.put(0, 0);

            HashMap<Integer, HashMap<Character, Integer>> answers = new HashMap<>();
            answers.put(0, new HashMap<>());

            final AtomicInteger groupID = new AtomicInteger(0);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (line.equals("")) {
                    answers.put(groupID.incrementAndGet(), new HashMap<>());
                    continue;
                }

                groupCount.merge(groupID.get(), 1, Integer::sum);
                line.chars().mapToObj(c -> (char) c).forEach(
                        c -> answers.get(groupID.get()).put(c, answers.get(groupID.get()).merge(c, 1, Integer::sum))
                );
            }

            System.out.println("Part one: " +
                    answers
                            .values()
                            .stream()
                            .mapToInt(HashMap::size)
                            .sum()
            );

            System.out.println("Part two: " +
                    answers
                            .entrySet()
                            .stream()
                            .mapToLong(
                                    e -> e.getValue()
                                            .values()
                                            .stream()
                                            .filter(i -> i.equals(groupCount.get(e.getKey())))
                                            .count())
                            .sum()
            );

        } catch (Exception ignored) {
            System.out.println("Fuck Java");
        }
    }
}
