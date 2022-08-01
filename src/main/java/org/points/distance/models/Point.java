package org.points.distance.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@JsonDeserialize(builder = Point.PointBuilder.class)
public class Point {

    @JsonProperty("x")
    private final int x;
    @JsonProperty("y")
    private final int y;

    public double distanceTo(Point other) {
        // (int operation int) might overflow, cast to wider type to avoid it
        long a = (long) this.x - other.x;
        long b = (long) this.y - other.y;

        return Math.sqrt((a * a) + (b * b));
    }
}