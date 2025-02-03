package io.dev.env.receipt_processor.service;

import io.dev.env.receipt_processor.exception.ReceiptNotFoundException;
import io.dev.env.receipt_processor.model.Item;
import io.dev.env.receipt_processor.model.PointsResponse;
import io.dev.env.receipt_processor.model.Receipt;
import io.dev.env.receipt_processor.model.ReceiptResponse;
import io.dev.env.receipt_processor.repository.ReceiptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.UUID;

/**
 * Receipt Service handles the business logic required by Receipt controller
 */
@Service
public class ReceiptService {
    private static final Logger logger = LoggerFactory.getLogger(ReceiptService.class);

    @Autowired
    private ReceiptRepository receiptRepository;

    /**
     * Persists the receipt to a database
     * @param receipt The receipt to be processed, type: Receipt
     * @return The auto-generate uuid of the receipt, type: ReceiptResponse
     */
    public ReceiptResponse processReceipt(Receipt receipt) {
        receiptRepository.save(receipt);
        UUID id = receipt.getId();
        logger.info("Processed receipt with ID: {}", id);
        return new ReceiptResponse(id);
    }

    /**
     * Calculates and returns the points for a give receipt
     * @param id The uuid of the receipt to be processed, type: UUID
     * @return The points calculated, type: PointsResponse
     */
    public PointsResponse getPoints(UUID id) {
        Receipt receipt = receiptRepository.findById(id)
                .orElseThrow(() -> new ReceiptNotFoundException("No receipt found for that ID: " + id));
        int points = calculatePoints(receipt);
        logger.info("Calculated points for receipt ID {}: {}", id, points);
        return new PointsResponse(points);
    }

    /**
     * Helper method to calculate the points
     * @param receipt The Receipt, type: Receipt
     * @return The points, type: int
     */
    private int calculatePoints(Receipt receipt) {
        int points = 0;

        // Rule 1: One point for every alphanumeric character in the retailer name
        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        // Rule 2: 50 points if the total is a round dollar amount with no cents
        double total = Double.parseDouble(receipt.getTotal());
        if (total == (int) total) points += 50;

        // Rule 3: 25 points if the total is a multiple of 0.25
        if (total % 0.25 == 0) points += 25;

        // Rule 4: 5 points for every two items on the receipt
        points += (receipt.getItems().size() / 2) * 5;

        // Rule 5: If the trimmed length of the item description is a multiple of 3,
        // multiply the price by 0.2 and round up to the nearest integer
        for (Item item : receipt.getItems()) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                double price = Double.parseDouble(item.getPrice());
                points += (int) Math.ceil(price * 0.2);
            }
        }

        // Rule 6: 6 points if the day in the purchase date is odd
        int day = Integer.parseInt(receipt.getPurchaseDate().split("-")[2]);
        if (day % 2 != 0) points += 6;

        // Rule 7: 10 points if the time of purchase is after 2:00pm and before 4:00pm
        LocalTime purchaseTime = LocalTime.parse(receipt.getPurchaseTime());
        if (purchaseTime.isAfter(LocalTime.of(14, 0)) && purchaseTime.isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }

        return points;
    }
}