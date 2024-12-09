package com.amalitech.productmanagementsystem.Service;

import com.amalitech.productmanagementsystem.Entity.Product;
import com.amalitech.productmanagementsystem.Entity.ProductDocument;
import com.amalitech.productmanagementsystem.Exceptions.ProductNotFoundException;
import com.amalitech.productmanagementsystem.Repository.ProductImageRepository;
import com.amalitech.productmanagementsystem.Repository.ProductRepository;
import com.amalitech.productmanagementsystem.Requests.ProductRequest;
import com.amalitech.productmanagementsystem.Requests.ProductUpdateRequest;
import com.amalitech.productmanagementsystem.Response.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductImageService imageService;
    private final ProductImageRepository productImageRepository;

    @SneakyThrows
    public Product createProduct(ProductRequest productRequest) {
        Product product = Product.builder().name(productRequest.name()).
                quantity(productRequest.quantity())
                .dateCreated(LocalDateTime.now()).description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        Long referenceID = product.getID();
        var imageId = imageService.addThumbNail(referenceID, productRequest.name(),
                productRequest.file());
        product.setImageId(imageId);
        return productRepository.save(product);
    }

    public void deleteProduct(Long Id) {
        Optional<Product> optionalProduct = productRepository.findById(Id);

        optionalProduct.ifPresentOrElse((product) -> {
            productRepository.deleteById(Id);
            productImageRepository.deleteById(String.valueOf(Id));
        }, () -> {
            throw new RuntimeException("Product not found");
        });
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map((products) -> {
            Optional<ProductDocument> productDocument = productImageRepository.findByReferenceID(products.getImageId());

            if (productDocument.isEmpty()) {
                throw new RuntimeException(" empty document");
            }
            return new ProductResponse(products.getID(),
                    products.getName(), products.getDescription(), products.getQuantity(),
                    products.getPrice(), productDocument.get().getImage(), products.getDateCreated());
        }).toList();
    }

    @SneakyThrows
    @Transactional
    public Product updateProduct( ProductUpdateRequest updateRequest) {
        Optional<Product> optionalProduct = productRepository.findById(updateRequest.Id());
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("product not found");
        }
         Product product = optionalProduct.get();
        product.setName(updateRequest.name());
        product.setQuantity(updateRequest.quantity());
        product.setPrice(updateRequest.price());
        Optional<ProductDocument> thumbnailUpdate=
                productImageRepository.findByReferenceID(product.getID());
        if (thumbnailUpdate.isEmpty()) {
             throw  new ProductNotFoundException(" image not found");
        }
        imageService.addThumbNail(updateRequest.Id(), updateRequest.name(), updateRequest.file());
        productRepository.save(product);
        return product;
    }

    public ProductResponse getProduct(Long Id) {
        Optional<Product> optionalProduct = productRepository.findById(Id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("product not found");
        }
        Optional<ProductDocument> productDocument = productImageRepository.findByReferenceID(optionalProduct.get().getImageId());
        if (productDocument.isEmpty()) {
            throw new RuntimeException(" image not found");
        }
         Product product = optionalProduct.get();
        return new ProductResponse(product.getID(), product.getName(),  product.getDescription(),
                product.getQuantity(), product.getPrice(), productDocument.get().getImage(),product.getDateCreated());
    }

    public List<ProductResponse> searchProduct(String name) {
      return  productRepository.findByAlikeTerms(name);
    }

}
