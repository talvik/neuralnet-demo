package neuralnet.util;

import neuralnet.model.DataSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;

public class CsvReader {

    public static final String RESULT_SEPARATOR = "\\|";
    public static final String VALUE_SEPARATOR = ",";

    public static DataSet read(Path path) throws IOException {
        BufferedReader reader = Files.newBufferedReader(path);
        DataSet dataSet = new DataSet();
        int lineCount = 0;

        while (reader.ready()) {
            String line = reader.readLine();
            lineCount++;
            if (line.startsWith("#")) continue;

            try {
                String[] data = line.split(RESULT_SEPARATOR);
                dataSet.add(toArray(data[0]), toArray(data[1]));
            } catch (Exception e) {
                throw new IOException("Error in line: " + lineCount + " St:" + line, e);
            }
        }

        return dataSet;
    }

    private static float[] toArray(String data) {
        String[] valuesSt = data.split(VALUE_SEPARATOR);
        float[] values = new float[valuesSt.length];

        for (int i = 0; i < valuesSt.length; i++) {
            values[i] = Float.parseFloat(valuesSt[i]);
        }
        return values;
    }
}
