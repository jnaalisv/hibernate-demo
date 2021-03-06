package com.example.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @EmbeddedId
    private ProductId id;

    private String name;

    private Product() {}

    public Product(String name) {
        id = ProductId.generateNew();
        this.name = name;
    }

    public ProductId getId() {
        return id;
    }
}
