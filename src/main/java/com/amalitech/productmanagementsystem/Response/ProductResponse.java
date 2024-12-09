package com.amalitech.productmanagementsystem.Response;

import org.bson.types.Binary;

import java.time.LocalDateTime;

public record ProductResponse(Long Id,String name,String description,  int quantity, double price, Binary image, LocalDateTime  createdAt) {
}
