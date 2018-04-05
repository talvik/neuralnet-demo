package neuralnet;

import com.google.common.collect.Lists;
import neuralnet.model.Score;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class XorTest {

    NeuralNet net = new NeuralNet(2, 1, new int[]{ 4, 4 });
    List<float[]> data = Lists.newArrayList(
            new float[]{0f, 0f},
            new float[]{0f, 1f},
            new float[]{1f, 0f},
            new float[]{1f, 1f}
    );
    List<float[]> result = Lists.newArrayList(
            new float[]{0f},
            new float[]{1f},
            new float[]{1f},
            new float[]{0f}
    );

    @Before
    public void setup() {
        net.reset();
    }

    @Test
    public void testTraining() {

        for (int i = 0; i < 1_000; i++) {
            net.train(data, result, 10_000);
            Score score = net.test(data, result);
            if (score.getAccuracy() > 0.9f) {
                System.out.println("Iterations: " +i + ", Score: " + score);
                break;
            }
        }
    }

    @Test
    public void testUntrained() {
        Score score = net.test(data, result);
        Assert.assertNotNull(score);
    }


}
