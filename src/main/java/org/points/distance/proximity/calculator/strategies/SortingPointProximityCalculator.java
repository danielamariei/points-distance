package org.points.distance.proximity.calculator.strategies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.points.distance.comparators.PointProximityComparator;
import org.points.distance.models.Point;
import org.points.distance.readers.PointReader;
import org.points.distance.writers.PointWriter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
@Profile("proximity.strategy.sorting")
public class SortingPointProximityCalculator implements PointProximityCalculator {

    @Override
    public void determineProximalPointsFor(PointReader pointReader, PointWriter pointWriter, PointProximityComparator pointProximityComparator, int maxPoints) {
        List<Point> points = new LinkedList<>();
        pointReader.forEach(points::add);

        Point[] optimalPoints = sort(points, pointProximityComparator);
        pointWriter.write(Arrays.stream(optimalPoints).limit(maxPoints).collect(Collectors.toList()));
    }

    private Point[] sort(List<Point> points, PointProximityComparator pointProximityComparator) {
        Point[] optimalPoints = points.toArray(new Point[0]);
        Arrays.sort(optimalPoints, pointProximityComparator);
        return optimalPoints;
    }

}
