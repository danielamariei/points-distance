package org.points.distance.readers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.annotation.Testable;
import org.points.distance.models.Point;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Testable
class BinaryPointReaderTest {

    private static List<Arguments> testCases() {
        return Arrays.asList(
                Arguments.of(new BinaryPointReader("src/test/resources/test-cases/binary/3-points-example-1").read(), Arrays.asList(new Point(1, 1), new Point(2, 2), new Point(-3, -3))),
                Arguments.of(new BinaryPointReader("src/test/resources/test-cases/binary/3-points-example-2").read(), Arrays.asList(new Point(-715, 22165), new Point(761, -23591), new Point(-194, 6014)))
        );
    }


    @ParameterizedTest
    @MethodSource("testCases")
    void givenAFileWithPoints_thenThePointAreDeserializedProperly(List<Point> deserializedPoints, List<Point> expectedPoints) throws IOException {
        Assertions.assertThat(deserializedPoints).containsExactlyElementsOf(expectedPoints);
    }

}