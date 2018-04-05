package neuralnet.util;

import neuralnet.model.DataSet;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class CsvReaderTest {
    @Test
    public void testCsvRead() throws IOException, URISyntaxException {
        Path csvPath = Paths.get(ClassLoader.getSystemResource("iris/iris.data").toURI());
        DataSet data = CsvReader.read(csvPath);
        assertNotNull(data);
        assertEquals("Expected number of entries",150, data.getSize());
        assertEquals("Expected number of inputs",4, data.getInputList().get(0).length);
    }

}