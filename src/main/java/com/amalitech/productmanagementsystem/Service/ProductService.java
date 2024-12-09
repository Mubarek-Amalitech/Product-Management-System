package com.amalitech.productmanagementsystem.Service;

import com.amalitech.productmanagementsystem.Entity.Product;
import com.amalitech.productmanagementsystem.Requests.ProductRequest;
import com.amalitech.productmanagementsystem.Requests.ProductUpdateRequest;
import com.amalitech.productmanagementsystem.Response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {



    Product createProduct(ProductRequest productRequest) ;
    ProductResponse getProduct(Long productId);
     Product  updateProduct(ProductUpdateRequest productUpdateRequest);
     List<ProductResponse> getAllProducts();
    List<ProductResponse> searchProduct(String name);
    void deleteProduct(Long productId);
}
