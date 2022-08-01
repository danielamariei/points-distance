package org.points.distance.readers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.annotation.Testable;
import org.points.distance.models.Point;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Testable
class JsonPointReaderTest {


    private static List<Arguments> testCases() {
        return Arrays.asList(
                Arguments.of(new JsonPointReader("src/test/resources/test-cases/json/center-axis.json").read(), Arrays.asList(new Point(0, 0)))
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void givenAFileWithPoints_thenThePointAreDeserializedProperly(List<Point> deserializedPoints, List<Point> exptectedPoints) throws IOException {
        Assertions.assertThat(deserializedPoints).containsExactlyElementsOf(exptectedPoints);
    }
}