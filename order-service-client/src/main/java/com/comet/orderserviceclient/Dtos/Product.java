package com.comet.orderserviceclient.Dtos;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long product_id;
    @NotNull
    private String product_name;

    @OneToMany(targetEntity = Order.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private List<Order> orders;

    public Product() {
    }

    public Product(String product_name) {
        this.product_name = product_name;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                '}';
    }
}
