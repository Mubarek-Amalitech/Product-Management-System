package com.amalitech.productmanagementsystem.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  ID;
    private  String name;
    private  int quantity;
    private LocalDateTime dateCreated;
    private   String description;
    private  Long imageId;
    private  double price;
}
