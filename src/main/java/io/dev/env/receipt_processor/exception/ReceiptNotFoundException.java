package io.dev.env.receipt_processor.exception;

/**
 * Exception thrown when a receipt is not found in the database.
 * <p>
 * This is a custom runtime exception used to indicate that a requested
 * {@code Receipt} entity does not exist. It extends {@link RuntimeException}
 * so that it can be used without requiring explicit exception handling.
 * </p>
 */
public class ReceiptNotFoundException extends RuntimeException {
    public ReceiptNotFoundException(String message) {
        super(message);
    }
}