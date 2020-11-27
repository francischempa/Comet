package com.exchangeconnectivity.exchangeconnectivity.Dtos;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;
    @NotNull
    private String product_name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    private List<Order> order;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<PortoflioContent> portoflioContents;

    public Product() {
    }

    public Product(String product_name) {
        this.product_name = product_name;
    }

    public Product(String product_name, List<Order> order, List<PortoflioContent> portoflioContents) {
        this.product_name = product_name;
        this.order = order;
        this.portoflioContents = portoflioContents;
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

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public List<PortoflioContent> getPortoflioContents() {
        return portoflioContents;
    }

    public void setPortoflioContents(List<PortoflioContent> portoflioContents) {
        this.portoflioContents = portoflioContents;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                '}';
    }
}
