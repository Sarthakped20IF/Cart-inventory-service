package com.example.inventory_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "inventory")
@Data
public class Inventory {
    @Id
    private String productId;
    @Column(nullable = false)
    private int availableQuantity;

//    @Version
//    private int version;

}
