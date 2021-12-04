package AdventOfCode.Jul2020.Day11;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class Main {

    static char[][] seats;

    public static void main(String[] args) {
        try {
            seats =
                Files
                    .readAllLines(Path.of("src\\AdventOfCode\\Day11\\Input"))
                    .stream()
                    .map(String::toCharArray)
                    .toArray(char[][]::new);

            System.out.println("Part one: " + doSteps(true));
            System.out.println("Part two: " + doSteps(false));

            String[][] matrix = {
                    {"a", "b", "c"},
                    {"d", "e"},
                    {"f"},
                    {"g", "h", "i", "j"}
            };

            String[] array = Stream.of(matrix)
                    .flatMap(Stream::of)
                    .toArray(String[]::new);

            System.out.println(Arrays.deepToString(matrix));
            System.out.println(Arrays.deepToString(array));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fuck Java");
        }
    }

    static int doSteps(boolean partOne) {
        char[][] prev;
        char[][] next = seats;

        do {
            prev = next;
            next = partOne ? partOne(prev) : partTwo(prev);
        } while (!Arrays.deepEquals(prev, next));

        int occupiedSeats = 0;
        for (char[] seatRow : prev) {
            for (char seat : seatRow) {
                if (seat == '#') {
                    occupiedSeats++;
                }
            }
        }
        return occupiedSeats;
    }

    static char[][] partOne(char[][] prevSeats) {

        char[][] nextSeats = Stream.of(prevSeats).map(char[]::clone).toArray($ -> prevSeats.clone());

        for (int y = 0; y < prevSeats.length; y++) {
            for (int x = 0; x < prevSeats[0].length; x++) {
                int adjacentOccupied = partOneAdjacentOccupied(prevSeats, x, y);
                if (prevSeats[y][x] == 'L' && adjacentOccupied == 0) {
                    nextSeats[y][x] = '#';
                } else if (prevSeats[y][x] == '#' && adjacentOccupied >= 4) {
                    nextSeats[y][x] = 'L';
                }
            }
        }

        return nextSeats;
    }

    static int partOneAdjacentOccupied(char[][] prevSeats, int x, int y) {

        int[] dx = new int[] {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = new int[] {-1, 0, 1, -1, 1, -1, 0, 1};

        int count = 0;

        for (int i = 0; i < 8; i++) {
            if (y + dy[i] < 0 || y + dy[i] > prevSeats.length - 1 ||
                x + dx[i] < 0 || x + dx[i] > prevSeats[0].length - 1) {
                continue;
            }
            if (prevSeats[y + dy[i]][x + dx[i]] == '#') {
                count++;
            }
        }

        return count;
    }

    static char[][] partTwo(char[][] prevSeats) {

        char[][] nextSeats = Stream.of(prevSeats).map(char[]::clone).toArray($ -> prevSeats.clone());

        for (int y = 0; y < prevSeats.length; y++) {
            for (int x = 0; x < prevSeats[0].length; x++) {

                int adjacentOccupied = partTwoAdjacentOccupied(prevSeats, x, y);

                if (prevSeats[y][x] == 'L' && adjacentOccupied == 0) {
                    nextSeats[y][x] = '#';
                } else if (prevSeats[y][x] == '#' && adjacentOccupied >= 5) {
                    nextSeats[y][x] = 'L';
                }
            }
        }

        return nextSeats;
    }

    static int partTwoAdjacentOccupied(char[][] prevSeats, int x, int y) {

        int[] dx = new int[] {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = new int[] {-1, 0, 1, -1, 1, -1, 0, 1};

        int count = 0;

        for (int i = 0; i < 8; i++) {
            if (firstSeatOccupied(prevSeats, x, y, dx[i], dy[i])) {
                count++;
            }
        }

        return count;
    }

    static boolean firstSeatOccupied(char[][] prevSeats, int x, int y, int dx, int dy) {
        if (y + dy < 0 || y + dy > prevSeats.length - 1 || x + dx < 0 || x + dx > prevSeats[0].length - 1) { return false; }
        else if (prevSeats[y + dy][x + dx] == '#') { return true; }
        else if (prevSeats[y + dy][x + dx] == 'L') { return false; }
        else {
            return firstSeatOccupied(prevSeats, x + dx, y + dy, dx, dy);
        }
    }
}
