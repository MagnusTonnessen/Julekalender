package AdventOfCode.Jul2020;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Day17 {

    static char[][] grid;
    static Set<Coordinate> coordinates = new HashSet<>();

    public static void main(String[] args) {
        try {
            grid = Files
                    .readAllLines(Path.of("src\\Input"))
                    .stream()
                    .map(String::toCharArray)
                    .toArray(char[][]::new);

            initialize();
            for (int i = 0; i < 6; i++) { stepPartOne(); }
            System.out.println("Part one: " + coordinates.size());

            initialize();
            for (int i = 0; i < 6; i++) { stepPartTwo(); }
            System.out.println("Part two: " + coordinates.size());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fuck Java");
        }
    }

    static void initialize() {
        coordinates.clear();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == '#') {
                    coordinates.add(new Coordinate(x - grid[0].length/2, y - grid.length/2));
                }
            }
        }
    }

    static void stepPartOne() {
        Set<Coordinate> copy = new HashSet<>(coordinates);

        for (int z = -10; z <= 10; z++) {
            for (int y = -10; y <= 10; y++) {
                for (int x = -10; x <= 10; x++) {
                    Coordinate coordinate = new Coordinate(x, y, z);
                    if (becomesActive(coordinate)) {
                        copy.add(coordinate);
                    }
                }
            }
        }

        coordinates.stream().filter(Day17::becomesInactive).forEach(copy::remove);
        coordinates = copy;
    }

    static void stepPartTwo() {
        Set<Coordinate> copy = new HashSet<>(coordinates);

        for (int w = -10; w <= 10; w++) {
            for (int z = -10; z <= 10; z++) {
                for (int y = -10; y <= 10; y++) {
                    for (int x = -10; x <= 10; x++) {
                        Coordinate coordinate = new Coordinate(x, y, z, w);
                        if (becomesActive(coordinate)) {
                            copy.add(coordinate);
                        }
                    }
                }
            }
        }

        coordinates.stream().filter(Day17::becomesInactive).forEach(copy::remove);
        coordinates = copy;
    }

    static boolean becomesInactive(Coordinate coordinate) {
        return activeNeighbours(coordinate) < 2 || 3 < activeNeighbours(coordinate);
    }

    static boolean becomesActive(Coordinate coordinate) {
        return !coordinates.contains(coordinate) && activeNeighbours(coordinate) == 3;
    }

    static long activeNeighbours(Coordinate coordinate) {
        return activeNeighbours(coordinate.x(), coordinate.y(), coordinate.z(), coordinate.w());
    }

    static long activeNeighbours(int x, int y, int z, int w) {

        // Denne approachen var litt enklere i et 2D grid ¯\_(ツ)_/¯

        int[] dx = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] dy = {-1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] dz = {-1, -1, -1, 0, 0, 0, 1, 1, 1, -1, -1, -1, 0, 0, 0, 1, 1, 1, -1, -1, -1, 0, 0, 0, 1, 1, 1, -1, -1, -1, 0, 0, 0, 1, 1, 1, -1, -1, -1, 0, 0, 1, 1, 1, -1, -1, -1, 0, 0, 0, 1, 1, 1, -1, -1, -1, 0, 0, 0, 1, 1, 1, -1, -1, -1, 0, 0, 0, 1, 1, 1, -1, -1, -1, 0, 0, 0, 1, 1, 1};
        int[] dw = {-1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1};

        return IntStream.range(0, 80).filter(i -> coordinates.contains(new Coordinate(x + dx[i], y + dy[i], z + dz[i], w + dw[i]))).count();
    }

    static record Coordinate(int x, int y, int z, int w) {

        public Coordinate(int x, int y) {
            this(x, y, 0, 0);
        }

        public Coordinate(int x, int y, int z) {
            this(x, y, z, 0);
        }
    }
}
