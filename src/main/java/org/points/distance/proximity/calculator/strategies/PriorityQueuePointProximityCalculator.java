package org.points.distance.proximity.calculator.strategies;

import com.google.common.collect.MinMaxPriorityQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.points.distance.comparators.PointProximityComparator;
import org.points.distance.models.Point;
import org.points.distance.readers.PointReader;
import org.points.distance.writers.PointWriter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@RequiredArgsConstructor
@Slf4j
@Component
@Profile({"proximity.strategy.priority.queue"})
public class PriorityQueuePointProximityCalculator implements PointProximityCalculator {

    @Override
    public void determineProximalPointsFor(PointReader pointReader, PointWriter pointWriter, PointProximityComparator pointProximityComparator, int maxPoints) {
        log.info("Priority Queue Strategy: computing optimal points.");
        MinMaxPriorityQueue<Point> optimalPoints = getPriorityQueueFor(pointProximityComparator, maxPoints);

        pointReader.forEach(optimalPoints::add);
        pointWriter.write(optimalPoints);
    }

    private MinMaxPriorityQueue<Point> getPriorityQueueFor(PointProximityComparator pointProximityComparator, int maxPoints) {
        return MinMaxPriorityQueue.orderedBy(pointProximityComparator).maximumSize(maxPoints).create();
    }

}
