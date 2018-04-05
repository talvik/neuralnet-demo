package neuralnet.activation;

//TODO point implemention ot StackOverflow
public class RoughSigmoid implements ActivationFunction {

    @Override
    public float apply(float x) {
        float x1 = Math.abs(x);
        float x2 = x1*x1;
        float e = 1.0f + x1 + x2*0.555f + x2*x2*0.143f;
        return 1.0f / (1.0f + (x > 0 ? 1.0f / e : e));
    }

}