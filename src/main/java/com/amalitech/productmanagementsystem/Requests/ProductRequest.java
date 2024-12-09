package com.amalitech.productmanagementsystem.Requests;


import org.springframework.web.multipart.MultipartFile;

public record ProductRequest(String name,  String description, int quantity , double price,  MultipartFile file) {
}
