package org.points.distance.writers;

import org.points.distance.models.Point;

import java.util.List;

public interface PointWriter {
    void write(Point p);
    void write(Iterable<Point> points);
}
