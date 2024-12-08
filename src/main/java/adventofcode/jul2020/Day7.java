package adventofcode.jul2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Day7 {
    public static void main(String[] args) {
        try {
            File file = new File("src\\Input.txt");
            Scanner sc = new Scanner(file);

            Map<String, Map<String, Integer>> bags = new HashMap<>();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.endsWith("no other bags.")) {
                    bags.put(line.replace(" bags contain no other bags.", ""), new HashMap<>());
                } else {
                    String bag = line.split(" bags")[0];
                    String[] contains = line.split("contain ", 2)[1].replaceAll(" bags| bag|\\.", "").split(", ");
                    Map<String, Integer> map = Arrays.stream(contains).collect(Collectors.toMap(newBag -> newBag.split(" ", 2)[1], newBag -> Integer.parseInt(newBag.split(" ")[0])));
                    bags.put(bag, map);
                }
            }

            System.out.println("Part one: " + partOne(bags).size());
            System.out.println("Part two: " + partTwo(bags).values().stream().reduce(0, Integer::sum));
        } catch (FileNotFoundException e) {
            System.out.println("Fuck Java");
        }
    }

    public static Set<String> partOne(Map<String, Map<String, Integer>> allBags) {
        Set<String> finalBags = new HashSet<>();
        Set<String> currentBags = allBags.keySet().stream().filter(key -> allBags.get(key).containsKey("shiny gold")).collect(Collectors.toSet());

        while (!currentBags.isEmpty()) {
            Set<String> tempBags = new HashSet<>(currentBags);
            currentBags.clear();

            tempBags.forEach(
                    currBag -> currentBags.addAll(allBags.keySet().stream().filter(key -> allBags.get(key).keySet().stream().anyMatch(b -> b.contains(currBag))).collect(Collectors.toSet()))
            );
            finalBags.addAll(tempBags);
        }

        return finalBags;
    }

    public static Map<String, Integer> partTwo(Map<String, Map<String, Integer>> allBags) {
        Map<String, Integer> finalBagCount = allBags.get("shiny gold").entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Map<String, Integer> currentBagCount = new HashMap<>(finalBagCount);

        while (!currentBagCount.isEmpty()) {
            Map<String, Integer> tempBagCount = new HashMap<>(currentBagCount);
            currentBagCount.clear();

            tempBagCount.forEach((key, value) ->
                    allBags.get(key).forEach((k, v) ->
                            currentBagCount.put(k, currentBagCount.getOrDefault(k, 0) + v * value)
                    )
            );

            currentBagCount.forEach((key, value) ->
                finalBagCount.put(key, finalBagCount.getOrDefault(key, 0) + value)
            );
        }

        return finalBagCount;
    }
}
