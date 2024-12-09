package com.amalitech.productmanagementsystem.Entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@Document
public class ProductDocument {
    @Id
    private  Long Id;
    private String title;
    private Binary image;
    private  Long referenceID;

    public ProductDocument(String title, Long referenceID) {
         this.title=title;
          this.referenceID=referenceID;
    }
}
