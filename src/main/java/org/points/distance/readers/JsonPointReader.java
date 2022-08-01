package org.points.distance.readers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.points.distance.models.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Component
@Profile("json.reader")
public class JsonPointReader implements PointReader {

    @Value("${points.path}")
    private final String pointsPath;

    private ObjectMapper objectMapper = new JsonMapper();

    @Override
    public List<Point> read() {
        try {
            return objectMapper.readValue(new File(pointsPath), new TypeReference<>() {
            });
        } catch (IOException e) {
            log.warn("Could not read json contents.", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public Iterator<Point> iterator() {
        return read().iterator();
    }

}
