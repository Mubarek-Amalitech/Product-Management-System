package com.amalitech.productmanagementsystem.Repository;

import com.amalitech.productmanagementsystem.Entity.Product;
import com.amalitech.productmanagementsystem.Response.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long > {
    @Query("SELECT p from Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%',:name,'%')")
   List<ProductResponse> findByAlikeTerms(@Param("name") String name);
}
