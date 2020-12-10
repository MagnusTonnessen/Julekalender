package Knowit.Day2;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        List<Integer> valid = new LinkedList<>();
        int n = 0;
        while (n <= 5433000) {
            if (containsSeven(n)) {
                n += lastPrime(n);
            } else {
                valid.add(n);
            }
            n++;
        }
        System.out.println(valid.size());
    }

    static boolean containsSeven(int n) {
        return String.valueOf(n).contains("7");
    }

    static int lastPrime(int n) {
        return IntStream.rangeClosed(1, n).map(i -> n - i + 1).filter(Main::isPrime).findFirst().getAsInt();
    }

    static boolean isPrime(int n) {
        return IntStream.rangeClosed(2, (int) Math.sqrt(n)).noneMatch(i -> n % i == 0);
    }
}
