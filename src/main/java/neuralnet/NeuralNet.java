package neuralnet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import neuralnet.model.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

public class NeuralNet {
    private static final float STEP_SIZE = 0.1f;
    final Random rnd = new Random();
    final int numInputs;
    final int numOutputs;
    final List<Layer> layers = new ArrayList<>();
    final List<float[]> buffers  = new ArrayList<>();


    public NeuralNet(int numInputs, int numOutputs, int[] layerNeurons) {
        checkArgument(layerNeurons.length > 0);
        checkArgument(numInputs > 0);
        checkArgument(numOutputs > 0);

        this.numInputs = numInputs;
        this.numOutputs = numOutputs;

        int inputs = numInputs;
        for (int numNeurons : layerNeurons) {
            layers.add(new Layer(numNeurons, inputs));
            buffers.add(new float[numNeurons]);
            
            inputs = numNeurons;
        }
        layers.add(new Layer(numOutputs, inputs));
        buffers.add(new float[numOutputs]);
    }

    public Score train(List<float[]> inputList, List<float[]> resultList, int iterations) {
        //TODO improve and move validation, each array size should match
        checkArgument(inputList.size() > 0);
        checkArgument(resultList.size() > 0);
        checkArgument(inputList.size() == resultList.size());

        float previousDistance = test(inputList, resultList).getTotalDistance();
        Change change = new Change(0, 0, 0, 0f);

        for (int interation = 0; interation < iterations; interation++) {

            randomChange(change);
            apply(change);

            Score score = test(inputList, resultList);
            float totalDistance = score.getTotalDistance();

            if (totalDistance > previousDistance) {
                rollback(change);
            } else {
                previousDistance = totalDistance;
            }
        }
        rollback(change);
        
        return test(inputList, resultList);
    }


    public Score test(List<float[]> inputList, List<float[]> resultList) {
        //TODO improve and move validation, each array size should match
        checkArgument(inputList.size() > 0);
        checkArgument(resultList.size() > 0);
        checkArgument(inputList.size() == resultList.size());

        int correct = 0;
        float totalDistance = 0f;

        for (int i = 0; i < inputList.size(); i++) {
            float[] input = inputList.get(i);
            float[] expectedResult = resultList.get(i);

            float[] result = solve(input);

            float distance = distance(result, expectedResult);
            totalDistance += distance;

            for (int j = 0; j < result.length; j++) {
                if (Math.abs(result[j] - expectedResult[j]) < 0.1f) {
                    correct++;
                }
            }
        }

        return Score.builder()
                .accuracy(1f * correct / (resultList.size() * resultList.get(0).length))
                .avgDistance(totalDistance / resultList.size())
                .totalDistance(totalDistance)
                .build();
    }

    public float[] solve(float[] input) {
        float[] result = input;
        for (int j = 0; j < layers.size(); j++) {
            result = layers.get(j).fire(result, buffers.get(j));
        }
        return result;
    }

    private void randomChange(Change change) {
        int indexLayer = rnd.nextInt(layers.size());
        int indexNeuron = rnd.nextInt(layers.get(indexLayer).neurons.length);
        int indexWeight = rnd.nextInt(layers.get(indexLayer).neurons[indexNeuron].weights.length + 1);
        float diff = rnd.nextBoolean() ? STEP_SIZE : STEP_SIZE * -1f;

        change.layer = indexLayer;
        change.neuron = indexNeuron;
        change.weight = indexWeight;
        change.diff = diff;
    }

    private void apply(Change change) {
        Neuron neuron = layers.get(change.layer).neurons[change.neuron];
        if (change.weight == neuron.weights.length) {
            neuron.bias += change.diff;
        } else {
            neuron.weights[change.weight] += change.diff;
        }
    }
    
    private void rollback(Change change) {
        change.diff *= -1;
        apply(change);
        change.diff *= -1;
    }

    private float distance(float[] result, float[] expectedResult) {
        float distance = 0.0f;
        for (int i = 0; i < result.length; i++) {
            distance += Math.pow(result[i] - expectedResult[i], 2);
        }
        return (float) Math.sqrt(distance);
    }
    
    
    public void reset() {
        for (Layer layer : layers) {
            for (Neuron neuron : layer.neurons) {
                neuron.resetWeights();
            }
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    private static class Change {
        private int layer;
        private int neuron;
        private int weight;
        private float diff;
    }
}
