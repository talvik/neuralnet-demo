package neuralnet;

import neuralnet.activation.*;

import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

public class Neuron {

    final float[] weights;
    final ActivationFunction actFunc = Functions.ROUGH_SIGMOID.get();
    float bias = 0.0f;

    public Neuron(int numInputs) { //TODO change to proper theory name
        this.weights = new float[numInputs];
        resetWeights();
    }

    public float fire(float[] inputs) {
        checkArgument(inputs.length == weights.length);

        float result = bias;
        for (int i = 0; i < inputs.length; i++) {
            result += inputs[i] * weights[i];
        }
        return actFunc.apply(result);
    }

    public void setWeight(int index, float weight) {
        weights[index] = weight;
    }

    public void setBias(float bias) {
        this.bias = bias;
    }

    public void resetWeights() {
        Random rnd = new Random();
        for (int i = 0; i < weights.length; i++) {
            weights[i] = rnd.nextFloat();
        }
        bias = rnd.nextFloat();
    }
}
