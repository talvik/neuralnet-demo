package neuralnet;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import neuralnet.model.Score;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Random;

//TODO move to microbench library
public class BenchTest {
    
    private static final int DATA_SET_SIZE = 100_000;
    private static final int INPUTS_SIZE = 32;
    private static final int OUTPUTS_SIZE = 4;
    
    private static final List<float[]> data = Lists.newArrayList();
    private static final List<float[]> result = Lists.newArrayList();

    private NeuralNet net = new NeuralNet(32, 4, new int[]{16, 16, 16, 16});

    @BeforeClass
    public static void setupClass() {
        Random rnd = new Random();
        
        for (int i = 0; i < DATA_SET_SIZE; i++) {
            
            float[] inputs = new float[INPUTS_SIZE];
            for (int inputIndex = 0; inputIndex < INPUTS_SIZE; inputIndex++) {
                inputs[inputIndex] = rnd.nextFloat();
            }

            float[] outputs = new float[OUTPUTS_SIZE];
            for (int outputIndex = 0; outputIndex < OUTPUTS_SIZE; outputIndex++) {
                outputs[outputIndex] = rnd.nextFloat();
            }
         
            data.add(inputs);
            result.add(outputs);
        }
    }

    @Ignore @Test
    public void benchTestTest() {
        for (int run = 0; run < 6; run ++) {
            Stopwatch sw = Stopwatch.createStarted();
            float totalDistance = 0f;
            for (int i = 0; i < 20; i++) {
                Score score = net.test(data, result);
                totalDistance =+ score.getAvgDistance();
            }
            sw.stop();
            System.out.println("benchTestTest: Time: " + sw.toString() + ", Distance: " + totalDistance);
        }
    }

    @Ignore @Test
    public void benchTrainTest() {
        List<float[]> splitData = data.subList(0, 10_000);
        List<float[]> splitResult = data.subList(0, 10_000);

        List<float[]> testData = data.subList(10_000, 100_000);
        List<float[]> testResult = data.subList(10_000, 100_000);

        for (int run = 0; run < 6; run ++) {
            Stopwatch sw = Stopwatch.createStarted();
            float totalDistance = 0f;
            for (int i = 0; i < 1; i++) {
                Score score = net.train(splitData, splitResult,200);
                totalDistance =+ score.getAvgDistance();
            }
            sw.stop();
            System.out.println("benchTrainTest: Time: " + sw.toString() + ", Distance: " + totalDistance);
        }
        System.out.println("benchTrainTest: " + net.test(testData, testResult));
    }


    @Ignore @Test
    public void benchXor() {
        NeuralNet net = new NeuralNet(2, 1, new int[]{16, 16, 16, 16});

        XorTest xor = new XorTest();
        Stopwatch sw = Stopwatch.createStarted();
        Score score = net.train(xor.data, xor.result, 2_000_000);
        System.out.println("benchXor: Time: " + sw.toString() + ", Score: " + score);
    }

    
    
}
