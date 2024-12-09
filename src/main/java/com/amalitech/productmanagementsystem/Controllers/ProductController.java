package com.amalitech.productmanagementsystem.Controllers;
import com.amalitech.productmanagementsystem.Entity.Product;
import com.amalitech.productmanagementsystem.Requests.ProductRequest;
import com.amalitech.productmanagementsystem.Requests.ProductUpdateRequest;
import com.amalitech.productmanagementsystem.Response.ProductResponse;
import com.amalitech.productmanagementsystem.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;
    @PostMapping("/create-product")
  public   EntityModel<Product> createProduct(@ModelAttribute ProductRequest productRequest) {
         var product = productService.createProduct(productRequest);
        return EntityModel.of(product,
                linkTo(methodOn(ProductController.class).createProduct(productRequest)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getProduct(product.getID())).withRel("single product"),
                linkTo(methodOn(ProductController.class).getAll()).withRel("get-all"));
    }

    @GetMapping("/get-all")
     public CollectionModel<EntityModel<ProductResponse>> getAll() {
        List<EntityModel<ProductResponse>> products = productService.getAllProducts().stream().map(product -> EntityModel.of(product,
                linkTo(methodOn(ProductController.class).getAll()).withSelfRel(),
                linkTo(methodOn(ProductController.class).deleteProduct(product.Id())).withRel("/delete-product/{productId}"),
                linkTo(methodOn(ProductController.class).getProduct(product.Id())).withRel("single-product/{productId}"),
                linkTo(methodOn(ProductController.class).getAll()).withRel("get-all"))).
                toList();

        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAll()).withSelfRel());
    }

   public @PatchMapping("/update-product")
    EntityModel<Product> updateProduct ( @ModelAttribute  ProductUpdateRequest productUpdateRequest) {
        return EntityModel.of(productService.updateProduct(productUpdateRequest),
                linkTo(methodOn(ProductController.class).updateProduct(productUpdateRequest)).withSelfRel(),
                linkTo(methodOn(ProductController.class)).withRel("update-product"));
    }

     @GetMapping("/single-product/{productId}")
     public EntityModel<ProductResponse> getProduct (@PathVariable Long productId) {
           return  EntityModel.of(productService.getProduct(productId),
                   linkTo(methodOn(ProductController.class).getProduct(productId)).withSelfRel(),
                   linkTo(methodOn(ProductController.class).getAll()).withRel("get-all"));
     }

    @DeleteMapping("/delete-product/{productId}")
    ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/search-product")
     CollectionModel<EntityModel<ProductResponse>> searchProducts(String name) {
        List<EntityModel<ProductResponse>> searchResults= productService.searchProduct(name).stream().map(result-> EntityModel.of(result,linkTo(methodOn(ProductController.class).searchProducts(name)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAll()).withRel("get-all"))).toList();
         return  CollectionModel.of(searchResults,linkTo(methodOn(ProductController.class).searchProducts(name)).withSelfRel());
    }

}
