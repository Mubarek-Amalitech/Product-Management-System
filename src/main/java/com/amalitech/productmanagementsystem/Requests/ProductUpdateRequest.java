package com.amalitech.productmanagementsystem.Requests;

import org.springframework.web.multipart.MultipartFile;

public record ProductUpdateRequest(Long Id , String name, int quantity, MultipartFile file, double price) {
}
