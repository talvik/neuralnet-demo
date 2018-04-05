package neuralnet.model;

import lombok.Builder;
import lombok.Data;
import neuralnet.Layer;
import neuralnet.activation.ActivationFunction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@Builder
public class NeuralNetConf {
    
    private final List<Integer> sizeLayers;
    private final int inputs;
    private final int outputs;
    private final ActivationFunction activationFunction;
    private final Comparator<float[]> accuracyFunction;
    
}
