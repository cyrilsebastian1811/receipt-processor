package io.dev.env.receipt_processor.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Receipt {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private UUID id;

    @Schema(description = "Name of the retailer", example = "Target")
    @NotNull(message = "Retailer is required")
    @Pattern(regexp = "^[\\w\\s\\-&]+$", message = "Retailer name must contain only alphanumeric characters, spaces, hyphens, or ampersands")
    private String retailer;

    @Schema(description = "Date of purchase in YYYY-MM-DD format", example = "2022-01-01")
    @NotNull(message = "Purchase date is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Purchase date must be in YYYY-MM-DD format")
    private String purchaseDate;

    @Schema(description = "Time of purchase in HH:MM format", example = "13:01")
    @NotNull(message = "Purchase time is required")
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "Purchase time must be in HH:MM format (24-hour)")
    private String purchaseTime;

    @Schema(description = "List of items purchased")
    @NotNull(message = "Items are required")
    @Size(min = 1, message = "At least one item is required")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "receipt_id")
    @Valid
    private List<Item> items;

    @Schema(description = "Total amount paid", example = "35.35")
    @NotNull(message = "Total is required")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Total must be in the format 0.00")
    private String total;
}
