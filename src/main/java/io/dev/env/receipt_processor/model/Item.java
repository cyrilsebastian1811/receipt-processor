package io.dev.env.receipt_processor.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

/**
 * The Item class represents an item on a receipt.
 * It contains details such as the short description and price of the item.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private String id;

    @Schema(description = "Short description of the item", example = "Mountain Dew 12PK")
    @NotNull(message = "Short description is required")
    @Pattern(regexp = "^[\\w\\s\\-]+$", message = "Short description must contain only alphanumeric characters, spaces, or hyphens")
    private String shortDescription;

    @Schema(description = "Price of the item", example = "6.49")
    @NotNull(message = "Price is required")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Price must be in the format 0.00")
    private String price;

    public Item(String shortDescription, String price) {
        this.shortDescription = shortDescription;
        this.price = price;
    }
}
