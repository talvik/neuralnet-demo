package neuralnet;

import static com.google.common.base.Preconditions.checkArgument;

public class Layer {

    final Neuron[] neurons;
    final int numInputs;


    public Layer(int numNeurons, int numInputs) {
        this.numInputs = numInputs;
        this.neurons = new Neuron[numNeurons];
        for (int i = 0; i < numNeurons; i++) {
            neurons[i] = new Neuron(numInputs);
        }
    }

    public float[] fire(float[] inputs, float[] results) {
        for (int i = 0; i < neurons.length; i++) {
            results[i] = neurons[i].fire(inputs);
        }
        return results;
    }

    public float[] fire(float[] inputs) {
        return fire(inputs, new float[neurons.length]);
    }

}
