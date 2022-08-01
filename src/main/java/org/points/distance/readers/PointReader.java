package org.points.distance.readers;

import org.points.distance.models.Point;

import java.util.Iterator;
import java.util.List;

public interface PointReader extends Iterable<Point> {
    List<Point> read();

    @Override
    Iterator<Point> iterator();
}
