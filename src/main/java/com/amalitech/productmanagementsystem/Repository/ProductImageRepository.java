package com.amalitech.productmanagementsystem.Repository;

import com.amalitech.productmanagementsystem.Entity.Product;
import com.amalitech.productmanagementsystem.Entity.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductImageRepository extends MongoRepository<ProductDocument, String> {
    Optional<ProductDocument> findByReferenceID ( Long Id);
}
