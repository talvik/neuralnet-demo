package neuralnet.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import neuralnet.model.DataSet;

import java.util.Collections;
import java.util.Random;

public class DataSetUtil {

    private static final Random rnd = new Random();

    public static void shuffle(DataSet dataSet) {
        int size = dataSet.getSize();

        for (int i = 0; i < size; i++) {
            int randomIndex = rnd.nextInt(size);

            Collections.swap(dataSet.getInputList(), i, randomIndex);
            Collections.swap(dataSet.getResultList(), i, randomIndex);
        }
    }

    public static TrainTestSet split(DataSet dataSet, float trainingPercentage) {
        int size = dataSet.getSize();
        int trainingSize = Math.round(trainingPercentage * size);

        DataSet trainingSet = dataSet.subSet(0, trainingSize);
        DataSet testSet = dataSet.subSet(trainingSize, size);

        return new TrainTestSet(trainingSet, testSet);
    }

    public static void normalize(DataSet dataSet) {
        
    }

    @Data
    public static class TrainTestSet {
        private final DataSet trainDataSet;
        private final DataSet testDataSet;
    }
}
