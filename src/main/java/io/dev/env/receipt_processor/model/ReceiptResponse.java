package io.dev.env.receipt_processor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ReceiptResponse {
    private UUID id;
}