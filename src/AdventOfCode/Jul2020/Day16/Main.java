package AdventOfCode.Jul2020.Day16;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Main {

    static List<Rule> rules;
    static int[] myTicket;
    static List<List<Long>> tickets;

    public static void main(String[] args) {
        try {
            File file = new File("src\\AdventOfCode\\Day16\\Input");
            Scanner sc = new Scanner(file);

            sc.useDelimiter("\\Z");
            String[] allTheGoodShit = sc.next().split(System.lineSeparator() + System.lineSeparator());

            rules =
                Arrays
                    .stream(allTheGoodShit[0].split(System.lineSeparator()))
                    .map(
                        field -> {
                            int[] range =
                                Arrays
                                    .stream(field.split(": ")[1].replace(" or ", "-").split("-"))
                                    .mapToInt(Integer::parseInt)
                                    .toArray();
                            return new Rule(field.split(": ")[0], range[0], range[1], range[2], range[3]);
                        })
                    .collect(toList());

            myTicket =
                Arrays
                    .stream(allTheGoodShit[1].split(System.lineSeparator())[1].split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            tickets =
                Arrays
                    .stream(allTheGoodShit[2].split(System.lineSeparator()))
                    .skip(1)
                    .map(fields -> Arrays.stream(fields.split(",")).map(Long::parseLong).collect(toList()))
                    .collect(toList());

            System.out.println("Part one: " + partOne());
            System.out.println("Part two: " + partTwo());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fuck Java");
        }
    }

    static long partOne() {
        return
            tickets
                .stream()
                .flatMapToLong(list -> list.stream().mapToLong(Long::longValue))
                .filter(ticket -> rules.stream().noneMatch(rule -> rule.valid(ticket)))
                .sum();
    }

    static long partTwo() {

        List<List<Long>> valid = tickets.stream().filter(ticket -> ticket.stream().allMatch(n -> rules.stream().anyMatch(rule -> rule.valid(n)))).collect(toList());
        Map<Integer, List<Rule>> indexes = new HashMap<>();

        rules.forEach(rule ->
            IntStream
                .range(0, valid.get(0).size())
                .filter(i -> valid.stream().allMatch(ticket -> rule.valid(ticket.get(i))))
                .forEach(i -> {
                    indexes.putIfAbsent(i, new ArrayList<>());
                    indexes.get(i).add(rule);
                })
        );

        Optional<Map.Entry<Integer, List<Rule>>> rs;
        Set<Integer> indices = new HashSet<>();
        while ((rs = indexes.entrySet().stream().filter(e -> e.getValue().size() == 1 && !indices.contains(e.getKey())).findAny()).isPresent()) {
            Map.Entry<Integer, List<Rule>> ruleEntry = rs.get();
            int index = ruleEntry.getKey();
            Rule rule = ruleEntry.getValue().get(0);
            for (int i = 0; i < rules.size(); i++) {
                Map.Entry<Integer, List<Rule>> ticket = new ArrayList<>(indexes.entrySet()).get(i);
                if (ticket.getKey() != index) {
                    ticket.getValue().remove(rule);
                }
            }

            indices.add(index);
        }

        return indexes
                .entrySet()
                .stream()
                .filter(e -> e.getValue().stream().anyMatch(Rule::isDeparture))
                .mapToLong(e -> myTicket[e.getKey()])
                .reduce((a, b) -> a * b)
                .orElse(0L);
    }

    static class Rule {
        String field;
        int lower1;
        int upper1;
        int lower2;
        int upper2;

        Rule(String field, int lower1, int upper1, int lower2, int upper2) {
            this.field = field;
            this.lower1 = lower1;
            this.upper1 = upper1;
            this.lower2 = lower2;
            this.upper2 = upper2;
        }

        boolean valid(long n) {
            return (lower1 <= n && n <= upper1) || (lower2 <= n && n <= upper2);
        }

        boolean isDeparture() {
            return field.startsWith("departure");
        }
    }
}
