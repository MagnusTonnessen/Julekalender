package Kode24;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

public class Main {

    static List<String> products;

    public static void main(String[] args) throws IOException {
        products = Files.readAllLines(Path.of("src/Kode24/Input"));

        IntStream.range(2, products.size() - 2)
                 .filter(Main::containsThree)
                 .filter(Main::isPalindrome)
                 .filter(Main::threeInARow)
                 .filter(Main::uniqueBefore)
                 .mapToObj(i -> products.get(i))
                 .forEach(System.out::println);
    }

    static String getNumber(int idx) {
        return products.get(idx).split(" ")[0];
    }

    static String getProduct(int idx) {
        return products.get(idx).split(" ")[1];
    }

    static boolean isPalindrome(int idx) {
        String number = getNumber(idx);
        for (int i = 0; i < number.length() / 2; i++) {
            if (number.charAt(i) != number.charAt(number.length() - 1 - i)) { return false; }
        }
        return true;
    }

    static boolean containsThree(int idx) {
        return getNumber(idx).contains("3");
    }

    static boolean threeInARow(int idx) {
        return getProduct(idx).equals(getProduct(idx + 1)) &&
               getProduct(idx + 1).equals(getProduct(idx + 2));
    }

    static boolean uniqueBefore(int idx) {
        return !getProduct(idx).equals(getProduct(idx - 1)) &&
               !getProduct(idx).equals(getProduct(idx - 2)) &&
               !getProduct(idx - 1).equals(getProduct(idx - 2));
    }
}