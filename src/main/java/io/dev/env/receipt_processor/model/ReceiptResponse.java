package io.dev.env.receipt_processor.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ReceiptResponse {
    @Schema(description = "UUID for the saved receipt", example = "7fb1377b-b223-49d9-a31a-5a02701dd310")
    private UUID id;
}