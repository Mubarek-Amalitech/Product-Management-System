package com.amalitech.productmanagementsystem.Service;

import com.amalitech.productmanagementsystem.Entity.Product;
import com.amalitech.productmanagementsystem.Entity.ProductDocument;
import com.amalitech.productmanagementsystem.Repository.ProductImageRepository;
import com.amalitech.productmanagementsystem.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.rmi.server.UID;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProductImageService {
    private  final ProductImageRepository productImageRepository;
    private  final ProductRepository productRepository;
 public  Long addThumbNail(Long referenceID, String title , MultipartFile file) throws IOException {
      ProductDocument productDocument= new ProductDocument(title,referenceID);
      productDocument.setImage( new Binary( BsonBinarySubType.BINARY, file.getBytes()));
      productDocument.setReferenceID(referenceID);
       productDocument=productImageRepository.insert(productDocument);
       return productDocument.getReferenceID();
 }
}
