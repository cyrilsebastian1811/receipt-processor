package io.dev.env.receipt_processor.repository;

import io.dev.env.receipt_processor.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing {@link Receipt} entities.
 * <p>
 * This repository provides CRUD operations for the Receipt entity.
 * It extends {@link JpaRepository}, which includes methods for querying the database.
 * </p>
 */
@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {

    /**
     * Finds a {@link Receipt} by its unique identifier.
     *
     * @param id The UUID of the receipt.
     * @return An {@link Optional} containing the receipt if found, otherwise empty.
     */
    Optional<Receipt> findById(UUID id);
}
