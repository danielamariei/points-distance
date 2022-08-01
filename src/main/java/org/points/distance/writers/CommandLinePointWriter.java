package org.points.distance.writers;

import org.points.distance.models.Point;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Profile("command.line.writer")
public class CommandLinePointWriter implements PointWriter {
    @Override
    public void write(Point p) {
        Objects.requireNonNull(p);
        System.out.println(String.format("%d,%d", p.getX(), p.getY()));
    }

    @Override
    public void write(Iterable<Point> points) {
        Objects.requireNonNull(points);
        points.forEach(this::write);
    }

    public static void main(String[] args) {
        new CommandLinePointWriter().write(Arrays.asList(new Point(1, 2), new Point(3, 10)));
    }
}
