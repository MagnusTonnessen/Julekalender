package Knowit.Day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            Map<String, Integer> perCake = new HashMap<>() {{
                put("sukker", 2);
                put("mel", 3);
                put("melk", 3);
                put("egg", 1);
            }};

            System.out.println(
                Collections.min(
                    Arrays.stream(new Scanner(new File("src\\Knowit\\Day4\\Input")).useDelimiter("\\Z").next().replace(System.lineSeparator(), ",").replace(" ", "").split(","))
                        .collect(Collectors.groupingBy(i -> i.split(":")[0], Collectors.summingInt(i -> Integer.parseInt(i.split(":")[1]))))
                        .entrySet()
                        .stream()
                        .map(i -> i.getValue() / perCake.get(i.getKey()))
                        .collect(Collectors.toSet())
                )
            );

        } catch (FileNotFoundException e) {
            System.out.println("Fuck Java");
        }
    }
}
