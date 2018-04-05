package neuralnet.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Score {
    private final float accuracy;
    private final float avgDistance;
    private final float totalDistance;
}
