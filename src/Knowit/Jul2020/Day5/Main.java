package Knowit.Jul2020.Day5;

import java.awt.geom.Point2D;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        try {

            AtomicInteger x = new AtomicInteger();
            AtomicInteger y = new AtomicInteger();

            System.out.println(
                area(Files.readAllLines(Path.of("src\\Knowit\\Day5\\Input")).get(0)
                    .chars()
                    .mapToObj(c -> (char) c)
                    .map(c -> switch (c) {
                        case 'O' -> new Point2D.Double(x.get(), y.getAndIncrement());
                        case 'N' -> new Point2D.Double(x.get(), y.getAndDecrement());
                        case 'H' -> new Point2D.Double(x.getAndIncrement(), y.get());
                        case 'V' -> new Point2D.Double(x.getAndDecrement(), y.get());
                        default -> throw new IllegalStateException("Unexpected value: " + c);
                    })
                    .collect(Collectors.toList())
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static double area(List<Point2D.Double> vertices) {

        double sum = 0;

        for (int i = 0; i < vertices.size() ; i++) {
            if (i == 0) {
                sum += vertices.get(i).x * (vertices.get(i + 1).y - vertices.get(vertices.size() - 1).y);
            } else if (i == vertices.size() - 1) {
                sum += vertices.get(i).x * (vertices.get(0).y - vertices.get(i - 1).y);
            } else {
                sum += vertices.get(i).x * (vertices.get(i + 1).y - vertices.get(i - 1).y);
            }
        }

        return 0.5 * Math.abs(sum);

    }
}
