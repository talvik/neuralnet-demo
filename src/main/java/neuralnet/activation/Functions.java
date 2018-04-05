package neuralnet.activation;

// put formulas and references on comments
public enum Functions {
    SIGMOID((float x) -> (float) (1 / (1 + Math.exp(-x)))),
    TAHN((float x) -> (float) Math.tanh(x)),
    LINEAR((float x) -> x),
    SMOOTH_RELU((float x) -> (float) Math.log(1 + Math.exp(x))),
    ROUGH_SIGMOID(new RoughSigmoid());

    private final ActivationFunction function;

    public ActivationFunction get() {
        return function;
    }

    Functions(ActivationFunction function) {
        this.function = function;
    }
}
