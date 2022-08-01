package org.points.distance;

import org.junit.jupiter.api.Test;
import org.points.distance.configuration.ParametersConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(properties = {
        "points.path=resources/test-cases/binary/3-points-example-1",
        "reference.point.x=0",
        "reference.point.y=0",
        "max.points=3"})
@ContextConfiguration(classes = ParametersConfiguration.class)
@ActiveProfiles({"proximity.farthest", "proximity.strategy.priority.queue", "binary.reader", "command.line.writer"})
class ApplicationTests {

    @Test
    void contextLoads() {
    }

}
