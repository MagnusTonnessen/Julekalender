package AdventOfCode.Jul2020.Day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("src\\AdventOfCode\\Day4\\Input");
            Scanner sc = new Scanner(file);
            HashMap<String, String> allFields = new HashMap<>();
            int validPassPartOne = 0;
            int validPassPartTwo = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equals("")) {
                    if (validateFieldsPartOne(allFields)) { validPassPartOne++; }
                    if (validateFieldsPartTwo(allFields)) { validPassPartTwo++; }
                    allFields.clear();
                    continue;
                }
                Arrays
                    .stream(line.split(" "))
                    .filter(field -> !field.startsWith("cid"))
                    .forEach(field -> allFields.put(field.substring(0, 3), field.substring(4))
                );
            }

            if (validateFieldsPartOne(allFields)) { validPassPartOne++; }
            if (validateFieldsPartTwo(allFields)) { validPassPartTwo++; }

            System.out.println("Part one: " + validPassPartOne);
            System.out.println("Part two: " + validPassPartTwo);
        } catch (FileNotFoundException e) {
            System.out.println("Fuck Java");
        }
    }

    public static boolean validateFieldsPartOne(HashMap<String, String> fields) {
        return fields.size() == 7;
    }

    public static boolean validateFieldsPartTwo(HashMap<String, String> fields) {
        return  validateFieldsPartOne(fields) &&

                // Birth year
                Integer.parseInt(fields.get("byr")) >= 1920 &&
                Integer.parseInt(fields.get("byr")) <= 2002 &&

                // Issue year
                Integer.parseInt(fields.get("iyr")) >= 2010 &&
                Integer.parseInt(fields.get("iyr")) <= 2020 &&

                // Expire year
                Integer.parseInt(fields.get("eyr")) >= 2020 &&
                Integer.parseInt(fields.get("eyr")) <= 2030 &&

                // Height
                (fields.get("hgt").endsWith("cm") ?
                        Integer.parseInt(fields.get("hgt").replace("cm", "")) >= 150 &&
                        Integer.parseInt(fields.get("hgt").replace("cm", "")) <= 193 :
                        Integer.parseInt(fields.get("hgt").replace("in", "")) >= 59 &&
                        Integer.parseInt(fields.get("hgt").replace("in", "")) <= 76) &&

                // Hair color
                fields.get("hcl").length() == 7 &&
                fields.get("hcl").startsWith("#") &&
                fields.get("hcl").substring(1).chars().mapToObj(c -> (char) c).allMatch(c -> (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) &&

                // Eye color
                Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(fields.get("ecl")) &&

                // Personal ID
                fields.get("pid").length() == 9 &&
                fields.get("pid").chars().mapToObj(c -> (char) c).allMatch(Character::isDigit);
    }
}
