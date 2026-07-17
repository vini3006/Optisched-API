package com.vinibarros.optisched.dto.optimization;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ObjectiveWeightsInput(
        Double alpha,
        Double beta,
        Double gamma,
        Double delta
) {
    public static ObjectiveWeightsInput defaults() {
        return new ObjectiveWeightsInput(1.0, 1.0, 1.0, 1.0);
    }
}