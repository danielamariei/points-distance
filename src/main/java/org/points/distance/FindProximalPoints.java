package org.points.distance;

import lombok.RequiredArgsConstructor;
import org.points.distance.comparators.PointProximityComparator;
import org.points.distance.proximity.calculator.strategies.PointProximityCalculator;
import org.points.distance.readers.PointReader;
import org.points.distance.writers.PointWriter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class FindProximalPoints {

    private final PointProximityCalculator pointProximityCalculator;

    private final PointReader pointReader;
    private final PointWriter pointWriter;

    private final PointProximityComparator pointProximityComparator;

    private final int maxPoints;

    @PostConstruct
    public void findProximalPoints() {
        pointProximityCalculator.determineProximalPointsFor(pointReader, pointWriter, pointProximityComparator, maxPoints);
    }
}
