package org.points.distance.comparators;

import lombok.RequiredArgsConstructor;
import org.points.distance.models.Point;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@RequiredArgsConstructor
@Component
@Profile("proximity.farthest")
public class FarthestProximityComparator implements PointProximityComparator {

    private final Point reference;

    @Override
    public int compare(Point p1, Point p2) {
        double a = p1.distanceTo(reference);
        double b = p2.distanceTo(reference);

        return -Double.compare(a, b);
    }
}
