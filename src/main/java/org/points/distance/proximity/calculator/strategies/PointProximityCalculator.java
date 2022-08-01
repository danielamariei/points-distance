package org.points.distance.proximity.calculator.strategies;

import org.points.distance.comparators.PointProximityComparator;
import org.points.distance.readers.PointReader;
import org.points.distance.writers.PointWriter;

/**
 * Strategy interface to model the contract for different proximity calculator possibilities.
 */
public interface PointProximityCalculator {

    void determineProximalPointsFor(PointReader pointReader, PointWriter pointWriter, PointProximityComparator proximityComparator, int maxPoints);

}
