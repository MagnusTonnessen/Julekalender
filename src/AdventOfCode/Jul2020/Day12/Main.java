package AdventOfCode.Jul2020.Day12;

import java.awt.geom.Point2D;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    static Point2D shipPosPartOne = new Point2D.Double(0, 0);
    static int shipDir = 1; // 0 -> North, 1 -> East, 2 -> South, 3 -> West
    static int[] NS = {1, 0, -1, 0};
    static int[] EW = {0, 1, 0, -1};

    static Point2D shipPosPartTwo = new Point2D.Double(0, 0);
    static Point2D waypoint = new Point2D.Double(10, 1);

    public static void main(String[] args) {

        try {
            Files.readAllLines(Path.of("src\\AdventOfCode\\Day12\\Input")).forEach(op -> {
                movePartOne(op.charAt(0), Integer.parseInt(op.substring(1)));
                movePartTwo(op.charAt(0), Integer.parseInt(op.substring(1)));
            });
            System.out.println("Part one: " + manhattanDistance(shipPosPartOne.getX(), shipPosPartOne.getY()));
            System.out.println("Part two: " + manhattanDistance(shipPosPartTwo.getX(), shipPosPartTwo.getY()));

        } catch (Exception e) {
            System.out.println("Fuck Java");
        }
    }

    static void movePartOne(char op, int dist) {
        switch (op) {
            case 'N' -> shipPosPartOne.setLocation(shipPosPartOne.getX(), shipPosPartOne.getY() + dist);
            case 'S' -> shipPosPartOne.setLocation(shipPosPartOne.getX(), shipPosPartOne.getY() - dist);
            case 'E' -> shipPosPartOne.setLocation(shipPosPartOne.getX() + dist, shipPosPartOne.getY());
            case 'W' -> shipPosPartOne.setLocation(shipPosPartOne.getX() - dist, shipPosPartOne.getY());
            case 'L' -> shipDir = (shipDir + (360 - dist) / 90) % 4;
            case 'R' -> shipDir = (shipDir + dist / 90) % 4;
            case 'F' -> shipPosPartOne.setLocation(shipPosPartOne.getX() + EW[shipDir] * dist, shipPosPartOne.getY() + NS[shipDir] * dist);
        }
    }

    static void movePartTwo(char op, int dist) {
        switch (op) {
            case 'N' -> waypoint.setLocation(waypoint.getX(), waypoint.getY() + dist);
            case 'S' -> waypoint.setLocation(waypoint.getX(), waypoint.getY() - dist);
            case 'E' -> waypoint.setLocation(waypoint.getX() + dist, waypoint.getY());
            case 'W' -> waypoint.setLocation(waypoint.getX() - dist, waypoint.getY());
            case 'L' -> rotateWaypointClockwise(360 - dist);
            case 'R' -> rotateWaypointClockwise(dist);
            case 'F' -> shipPosPartTwo.setLocation(shipPosPartTwo.getX() + waypoint.getX() * dist, shipPosPartTwo.getY() + waypoint.getY() * dist);
        }
    }

    static void rotateWaypointClockwise(int dist) {
        switch (dist) {
            case 90  -> waypoint.setLocation(waypoint.getY(), -waypoint.getX());
            case 180 -> waypoint.setLocation(-waypoint.getX(), -waypoint.getY());
            case 270 -> waypoint.setLocation(-waypoint.getY(), waypoint.getX());
        }
    }

    static double manhattanDistance(double x1, double y1) {
        return Math.abs(x1) + Math.abs(y1);
    }
}