package org.points.distance.proximity.calculator.strategies;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.annotation.Testable;
import org.points.distance.comparators.ClosestProximityComparator;
import org.points.distance.comparators.FarthestProximityComparator;
import org.points.distance.comparators.PointProximityComparator;
import org.points.distance.models.Point;
import org.points.distance.readers.JsonPointReader;
import org.points.distance.readers.PointReader;
import org.points.distance.writers.PointWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Testable
class PointProximityCalculatorTest {

    private static List<Point> computedPoints = new LinkedList<>();

    @BeforeEach
    private void beforeEach() {
        computedPoints.clear();
    }

    private static List<Arguments> testCases() {
        return Arrays.asList(
                Arguments.of(new JsonPointReader("src/test/resources/test-cases/json/3-points.json"), new IdentityPointWriter(computedPoints), new ClosestProximityComparator(new Point(0, 0)), 1, Arrays.asList(new Point(1, 1))),
                Arguments.of(new JsonPointReader("src/test/resources/test-cases/json/3-points.json"), new IdentityPointWriter(computedPoints), new ClosestProximityComparator(new Point(0, 0)), 2, Arrays.asList(new Point(1, 1), new Point(2, 2))),
                Arguments.of(new JsonPointReader("src/test/resources/test-cases/json/3-points.json"), new IdentityPointWriter(computedPoints), new ClosestProximityComparator(new Point(0, 0)), 3, Arrays.asList(new Point(1, 1), new Point(2, 2), new Point(-4, -4))),
                Arguments.of(new JsonPointReader("src/test/resources/test-cases/json/5-points.json"), new IdentityPointWriter(computedPoints), new ClosestProximityComparator(new Point(100, 100)), 3, Arrays.asList(new Point(18, 18), new Point(20, 20), new Point(-400, -400))),
                Arguments.of(new JsonPointReader("src/test/resources/test-cases/json/5-points.json"), new IdentityPointWriter(computedPoints), new ClosestProximityComparator(new Point(-100, -100)), 3, Arrays.asList(new Point(18, 18), new Point(20, 20), new Point(-400, -400))),
                Arguments.of(new JsonPointReader("src/test/resources/test-cases/json/5-points.json"), new IdentityPointWriter(computedPoints), new FarthestProximityComparator(new Point(100, 100)), 3, Arrays.asList(new Point(-400, -400), new Point(32767, 32767), new Point(-32768, -32768))),
                Arguments.of(new JsonPointReader("src/test/resources/test-cases/json/5-points.json"), new IdentityPointWriter(computedPoints), new FarthestProximityComparator(new Point(-100, -100)), 3, Arrays.asList(new Point(-400, -400), new Point(32767, 32767), new Point(-32768, -32768)))
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void givenPointsToComputeProximityForUsingPriorityQueues_thenThePointCalculatorProducesTheAppropriatePoints(PointReader pointReader, PointWriter pointWriter, PointProximityComparator pointProximityComparator, int maxPoints, List<Point> expectedResults) throws IOException {
        PointProximityCalculator pointProximityCalculator = new PriorityQueuePointProximityCalculator();
        pointProximityCalculator.determineProximalPointsFor(pointReader, pointWriter, pointProximityComparator, maxPoints);
        Assertions.assertThat(computedPoints).hasSameElementsAs(expectedResults);
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void givenPointsToComputeProximityForUsingSorting_thenThePointCalculatorProducesTheAppropriatePoints(PointReader pointReader, PointWriter pointWriter, PointProximityComparator pointProximityComparator, int maxPoints, List<Point> expectedResults) throws IOException {
        PointProximityCalculator pointProximityCalculator = new SortingPointProximityCalculator();
        pointProximityCalculator.determineProximalPointsFor(pointReader, pointWriter, pointProximityComparator, maxPoints);
        Assertions.assertThat(computedPoints).hasSameElementsAs(expectedResults);
    }

    private static class IdentityPointWriter implements PointWriter {

        private List<Point> points;

        private IdentityPointWriter(List<Point> points) {
            this.points = points;
        }

        @Override
        public void write(Point p) {
            points.add(p);
        }

        @Override
        public void write(Iterable<Point> points) {
            points.forEach(this::write);
        }
    }
}