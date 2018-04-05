package neuralnet;

import com.google.common.base.Stopwatch;
import neuralnet.model.DataSet;
import neuralnet.model.Score;
import neuralnet.util.CsvReader;
import neuralnet.util.DataSetUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class IrisTest {

    DataSet train;
    DataSet test;
    
    
    @Before
    public void setup() throws Exception {
        Path csvPath = Paths.get(ClassLoader.getSystemResource("iris/iris.data").toURI());
        DataSet data = CsvReader.read(csvPath);
        DataSetUtil.shuffle(data);
        DataSetUtil.TrainTestSet trainAndTest = DataSetUtil.split(data, 0.80f);

        train = trainAndTest.getTrainDataSet();
        test = trainAndTest.getTestDataSet();
    }


    @Test
    public void training() throws Exception {
        NeuralNet nn = new NeuralNet(4,3, new int[]{8, 8, 8});

        Stopwatch sw = Stopwatch.createStarted();
        Score trainingScore = nn.train(train.getInputList(), train.getResultList(), 50_000);
        Score testScore = nn.test(test.getInputList(), test.getResultList());

        System.out.println("Training " + trainingScore + ",\tTest " + testScore + " ("+ sw +")");
    }

    @Ignore
    @Test
    public void longTraining() throws Exception {
        NeuralNet nn = new NeuralNet(4,3, new int[]{8, 8, 8});

        Path csvPath = Paths.get(ClassLoader.getSystemResource("iris/iris.data").toURI());
        DataSet data = CsvReader.read(csvPath);
        DataSetUtil.shuffle(data);
        DataSetUtil.TrainTestSet trainAndTest = DataSetUtil.split(data, 0.80f);

        DataSet train = trainAndTest.getTrainDataSet();
        DataSet test = trainAndTest.getTestDataSet();

        for (int i = 0; i < 20; i++) {
            Stopwatch sw = Stopwatch.createStarted();
            Score trainingScore = nn.train(train.getInputList(), train.getResultList(), 25_000);
            Score testScore = nn.test(test.getInputList(), test.getResultList());

            System.out.println(i + "\tTraining " + trainingScore + ",\tTest " + testScore + " ("+ sw +")");
        }
    }

}
