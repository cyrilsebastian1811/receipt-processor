package io.dev.env.receipt_processor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private String total;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "receipt_id")
    private List<Item> items;


//    // Getters and Setters
//    public String getId() { return id; }
//    public void setId(String id) { this.id = id; }
//
//    public String getRetailer() { return retailer; }
//    public void setRetailer(String retailer) { this.retailer = retailer; }
//
//    public String getPurchaseDate() { return purchaseDate; }
//    public void setPurchaseDate(String purchaseDate) { this.purchaseDate = purchaseDate; }
//
//    public String getPurchaseTime() { return purchaseTime; }
//    public void setPurchaseTime(String purchaseTime) { this.purchaseTime = purchaseTime; }
//
//    public List<Item> getItems() { return items; }
//    public void setItems(List<Item> items) { this.items = items; }
//
//    public String getTotal() { return total; }
//    public void setTotal(String total) { this.total = total; }
}
