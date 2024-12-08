package adventofcode.jul2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class Day8 {
    public static void main(String[] args) {
        try {
            File file = new File("src\\Input.txt");
            Scanner sc = new Scanner(file);

            HashMap<Integer, String> operations = new HashMap<>();

            int index = 0;


            while (sc.hasNext()) { operations.put(index++, sc.nextLine()); }

            System.out.println("Part one: " + partOne(operations));
            System.out.println("Part two: " + partTwo(operations));

        } catch (FileNotFoundException e) {
            System.out.println("Fuck Java");
        }
    }

    static int partOne(HashMap<Integer, String> operations) {

        HashSet<Integer> visited = new HashSet<>();
        int accumulator = 0;
        int index = 0;

        while (!visited.contains(index)) {

            String op = operations.get(index).split(" ")[0];
            int number = Integer.parseInt(operations.get(index).split(" ")[1]);

            visited.add(index);
            if (op.equals("acc")) { accumulator += number; }
            index += op.equals("jmp") ? number : 1;
        }
        return accumulator;
    }

    static int partTwo(HashMap<Integer, String> operations) {

        HashSet<Integer> visited = new HashSet<>();
        int accumulator = 0;
        int index;

        replace: for (int i = 0; i < operations.size(); i++) {

            Map<Integer, String> operationsCopy = new HashMap<>(operations);

            if (operationsCopy.get(i).startsWith("nop")) {
                operationsCopy.replace(i, operationsCopy.get(i).replace("nop", "acc"));
            } else if (operationsCopy.get(i).startsWith("jmp")) {
                operationsCopy.replace(i, operationsCopy.get(i).replace("jmp", "nop"));
            } else {
                continue;
            }

            index = 0;
            accumulator = 0;
            visited.clear();

            while (!visited.contains(index)) {

                String op = operationsCopy.get(index).split(" ")[0];
                int number = Integer.parseInt(operationsCopy.get(index).split(" ")[1]);
                visited.add(index);
                if (op.equals("acc")) { accumulator += number; }
                index += op.equals("jmp") ? number : 1;

                if (index == operationsCopy.size()) { break replace; }
            }
        }
        return accumulator;
    }
}
