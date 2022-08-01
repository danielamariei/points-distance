package org.points.distance.configuration;

import org.points.distance.models.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Take the command line arguments and construct beans that encapsulate the parameters.
 */
@Configuration
public class ParametersConfiguration {
    @Value("${points.path}")
    private String pointsPath;

    @Value("${max.points}")
    private int maxPoints;

    @Value("${reference.point.x}")
    private int x;

    @Value("${reference.point.y}")
    private int y;

    @Bean
    public String pointsPath() {
        return pointsPath;
    }

    @Bean
    public int maxPoints() {
        return maxPoints;
    }

    @Bean
    public Point reference() {
        return new Point(x, y);
    }

}
