package org.points.distance.writers;

import org.points.distance.models.Point;

public interface PointWriter {
    void write(Point p);

    void write(Iterable<Point> points);
}
