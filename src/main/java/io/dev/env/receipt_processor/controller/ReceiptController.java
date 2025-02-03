package io.dev.env.receipt_processor.controller;

import io.dev.env.receipt_processor.model.PointsResponse;
import io.dev.env.receipt_processor.model.Receipt;
import io.dev.env.receipt_processor.model.ReceiptResponse;
import io.dev.env.receipt_processor.service.ReceiptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * The ReceiptController class handles HTTP requests related to receipt processing.
 * It provides endpoints for submitting receipts and retrieving points awarded for a receipt.
 */
@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;

    /**
     * Submits a receipt for processing and returns a unique ID for the receipt.
     * @param receipt The receipt to be processed, provided as a JSON object in the request body.
     * @return A ResponseEntity containing the unique ID assigned to the receipt.
     */
    @Operation(summary = "Process a receipt", description = "Takes in a JSON receipt and returns a unique ID for the receipt.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receipt processed successfully",
                    content = @Content(schema = @Schema(implementation = ReceiptResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/process")
    public ResponseEntity<ReceiptResponse> processReceipt(@Valid @RequestBody Receipt receipt) {
        return ResponseEntity.ok(receiptService.processReceipt(receipt));
    }

    /**
     * Retrieves the points awarded for a receipt with the given ID.
     * @param id The unique ID of the receipt.
     * @return A ResponseEntity containing the points awarded for the receipt.
     */
    @Operation(summary = "Get points for a receipt", description = "Returns the points awarded for the receipt with the given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Points retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PointsResponse.class))),
            @ApiResponse(responseCode = "404", description = "Receipt not found")
    })
    @GetMapping("/{id}/points")
    public ResponseEntity<PointsResponse> getPoints(@PathVariable UUID id) {
        return ResponseEntity.ok(receiptService.getPoints(id));
    }
}
