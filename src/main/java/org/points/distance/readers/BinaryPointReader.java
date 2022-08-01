package org.points.distance.readers;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.points.distance.models.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
@Component
@Profile("binary.reader")
public class BinaryPointReader implements PointReader {

    @Value("${points.path}")
    private final String pointsPath;

    @Override
    public List<Point> read() {
        List<Point> points = new LinkedList<>();

        try (DataInputStream reader = getReader()) {

            while (true) {
                points.add(readNextPoint(reader));
            }

        } catch (EOFException e) {
            // End of file
            return points;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @SneakyThrows
    public Stream<Point> getPoints() {
        DataInputStream reader = getReader();
        return Stream.generate(() -> Try.of(() -> readNextPoint(reader))).filter(Try::isSuccess).map(Try::get);
    }


    // TODO: close file resources or use try with resources
    @SneakyThrows
    @Override
    public Iterator<Point> iterator() {
        return new BinaryPointIterator(getReader());
    }

    private DataInputStream getReader() throws FileNotFoundException {
        return new DataInputStream(new BufferedInputStream(new FileInputStream(pointsPath)));
    }

    private Point readNextPoint(DataInputStream reader) throws IOException {
        short x = reader.readShort();
        short y = reader.readShort();

        return new Point(x, y);

    }

    @RequiredArgsConstructor
    private static class BinaryPointIterator implements Iterator<Point> {
        private final DataInputStream dataInputStream;

        @SneakyThrows
        @Override
        public boolean hasNext() {
            try {
                // read at most 2 shorts
                saveFileCursorMarkerPoint();

                readNextPoint(dataInputStream);

                // enable the current point to be read when calling next
                rewindFileCursorAtMarkerPoint();
                return true;

            } catch (IOException e) {
                // not able to read or reached end of file, close stream, fail fast
                dataInputStream.close();
                return false;
            }
        }

        private void rewindFileCursorAtMarkerPoint() throws IOException {
            dataInputStream.reset();
        }

        private void saveFileCursorMarkerPoint() {
            dataInputStream.mark(Short.BYTES * 2);
        }

        @SneakyThrows
        @Override
        public Point next() {
            return readNextPoint(dataInputStream);
        }


        private Point readNextPoint(DataInputStream reader) throws IOException {
            short x = reader.readShort();
            short y = reader.readShort();

            return new Point(x, y);

        }
    }
}
