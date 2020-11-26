package com.comet.orderservice.Dto;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    @NotNull
    @Column(name = "product_name")
    private String productName;

    public Product() {
    }

    public Product(String product_name) {
        this.productName = product_name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", product_name='" + productName + '\'' +
                '}';
    }

    public Long getProduct_id() {
        return product_id;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}

