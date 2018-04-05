package neuralnet.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class DataSet {
    private final List<float[]> inputList;
    private final List<float[]> resultList;

    public DataSet() {
        this.inputList = new ArrayList<>();
        this.resultList = new ArrayList<>();
    }

    public void add(float[] input, float[] result) {
        inputList.add(input);
        resultList.add(result);
    }

    public int getSize() {
        return inputList.size();
    }

    public DataSet subSet(int offset, int size) {
        return new DataSet(
                inputList.subList(offset, size),
                resultList.subList(offset, size)
        );
    }
}
