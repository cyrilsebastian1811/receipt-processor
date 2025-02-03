package io.dev.env.receipt_processor.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointsResponse {
    @Schema(description = "Points awarded for the receipt", example = "100")
    private int points;
}
